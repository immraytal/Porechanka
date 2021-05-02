package com.kisel.Porechanka.service;

import com.kisel.Porechanka.api.dao.RoleDao;
import com.kisel.Porechanka.api.dao.UserDao;
import com.kisel.Porechanka.api.service.EmailService;
import com.kisel.Porechanka.api.service.UserService;
import com.kisel.Porechanka.model.Sex;
import com.kisel.Porechanka.model.UserModel;
import com.kisel.Porechanka.model.UserStatus;
import com.kisel.Porechanka.model.dto.UserModelCreateDto;
import com.kisel.Porechanka.model.dto.UserModelDto;
import com.kisel.Porechanka.util.DefaultValue;
import com.kisel.Porechanka.util.MyException;
import com.kisel.Porechanka.util.TokenUtil;
import com.kisel.Porechanka.util.validator.FieldValueExists;
import io.jsonwebtoken.lang.Assert;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@PropertySource("classpath:tokenKeys.properties")
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService, FieldValueExists {

    private static final Logger LOG = Logger.getLogger(UserServiceImpl.class);

    @Value("${RESET_SECRET}")
    private String resetSecret;

    @Value("secret")
    private String authSecret;

    @Autowired
    private TokenUtil tokenUtil;

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper modelMapper;

    //@Autowired
    private EmailService emailService;

    @Override
    public UserDetails loadUserByUsername(final String s) throws UsernameNotFoundException {
        final UserModel userModel = userDao.findByLogin(s);
        if (userModel == null) {
            LOG.error("User with " + s + " not found");
            throw new UsernameNotFoundException("Not found user");
        }
        final List<String> objects = new ArrayList<>();
        objects.add(userModel.getRole().getRoleName());
        return new User(userModel.getLogin(), userModel.getEncryptedPassword(), !(userModel.getUserStatus().equals(UserStatus.BANNED)), true, true, !(userModel.getUserStatus().equals(UserStatus.BANNED)), objects.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));

    }

    @Override
    public UserModelDto findByLogin(String login) {
        return modelMapper.map(userDao.findByLogin(login), UserModelDto.class);
    }

    @Override
    public UserModelDto save(UserModelCreateDto userModelCreateDto) {
        UserModel userModel = new UserModel();
        userModel.setCreatedAccountDate(LocalDate.now());
        userModel.setCreatedAccountTime(LocalTime.now());
        userModel.setEncryptedPassword(passwordEncoder.encode(userModelCreateDto.getPassword()));
        userModel.setRole(roleDao.findByName(DefaultValue.USER_ROLE));
        userModel.setLogin(userModelCreateDto.getLogin());
        userModel.setBirth(userModelCreateDto.getBirth());
        userModel.setAge(calculateAge(userModel.getBirth()));
        userModel.setEmail(userModelCreateDto.getEmail());
        userModel.setFirstName(userModelCreateDto.getFirstName());
        userModel.setSurName(userModelCreateDto.getSurName());
        userModel.setSex(parseSex(userModelCreateDto.getSex()));
        userModel.setUserStatus(UserStatus.ACTIVE);
        userModel.setRating(0);
        userModel.setPhoneNumber(userModelCreateDto.getPhoneNumber());
        userDao.save(userModel);
        LOG.info("Saving user " + userModel.getId() + " with a login: " + userModel.getLogin());
        return modelMapper.map(userModel, UserModelDto.class);
    }

    private Sex parseSex(String sex) {
        if (!sex.isBlank() && !sex.isEmpty()) {
            if (sex.toLowerCase().contains("female")) {
                return Sex.FEMALE;
            } else if (sex.toLowerCase().contains("male")) {
                return Sex.MALE;
            } else {
                LOG.error("Incorrect string Sex");
                throw new MyException("Incorrect sex, try again ;)");
            }
        }
        LOG.error("String Sex is empty");
        throw new MyException("Incorrect sex (empty), try again ;)");
    }

    private short calculateAge(LocalDate birth) {
        short age = ((short) Period.between(birth, LocalDate.now()).getYears());
        if (age < 0 || age > 120) {
            LOG.error("Incorrect date birth, impossible age");
            throw new MyException("Incorrect date birth, impossible age");
        }
        return age;
    }

    @Override
    public UserModelDto update(UserModelCreateDto userModelCreateDto, Long userId) {
        UserModel userModel = userDao.getById(userId);
        userModel.setEncryptedPassword(passwordEncoder.encode(userModelCreateDto.getPassword()));
        userModel.setSurName(userModelCreateDto.getSurName());
        userModel.setFirstName(userModelCreateDto.getFirstName());
        userModel.setSex(parseSex(userModelCreateDto.getSex()));
        String phone = userModelCreateDto.getPhoneNumber();
        if (userDao.findByPhoneNumber(phone).getId().equals(userId)) {
            userModel.setPhoneNumber(phone);
        } else {
            LOG.error("Failed updating user " + userId + " id, new phone already used");
            throw new MyException("Entered phone already used");
        }

        String newEmail = userModelCreateDto.getEmail();

        if (userDao.findByEmail(newEmail).getId().equals(userId)) {
            userModel.setEmail(newEmail);
        } else {
            LOG.error("Failed updating user " + userId + " id, new email already used");
            throw new MyException("Entered email already used");
        }
        userDao.update(userModel);
        LOG.info("Updating user " + userModel.getId() + " with a login: " + userModel.getLogin());
        return modelMapper.map(userModel, UserModelDto.class);
    }


    @Override
    public UserModelDto getById(long id) {
        UserModel user = userDao.getById(id);
        short age = calculateAge(user.getBirth());
        if (age != user.getAge()) {
            user.setAge(age);
        }
        return modelMapper.map(user, UserModelDto.class);
    }

    @Override
    public UserModelDto toBanUser(long id, long currentAdminId) {
        UserModel user = userDao.getById(id);
        user.setUserStatus(UserStatus.BANNED);
        userDao.update(user);
        LOG.info("User #" + id + " is banned by Admin #" + currentAdminId);
        return modelMapper.map(user, UserModelDto.class);
    }

    @Override
    public void generateAndSendResetToken(String email) {
        UserModel userModel = userDao.findByEmail(email);
        String token = tokenUtil.generateJwtToken(userModel.getLogin(), resetSecret);
        emailService.sendMailWithResetToken(userModel.getFirstName(), email, token);
    }

    @Override
    public boolean fieldValueExists(Object value, String fieldName) {
        Assert.notNull(fieldName);
        return false;
//
//        if (value == null) {
//            return false;
//        }
//        try {
//            switch (fieldName) {
//                case "email":
//                    userDao.findByEmail(value.toString());
//                    return true;
//                case "login":
//                    userDao.findByLogin(value.toString());
//                    return true;
//                case "phoneNumber":
//                    userDao.findByPhoneNumber(value.toString());
//                    return true;
//                default:
//                    LOG.error("Field name not supported");
//                    throw new UnsupportedOperationException("Field name not supported");
//            }
//        } catch (NoResultException e) {
//            return false;
//        }
    }

    @Override
    public String validateResetToken(String token) {
        String login = tokenUtil.getSubjectFromToken(token, resetSecret);
        UserModel user = userDao.findByLogin(login);
        if (user.getUserStatus() == UserStatus.ACTIVE) {
            return tokenUtil.generateJwtToken(login, authSecret);
        } else {
            LOG.error("Entered wrong token");
            throw new MyException("Invalid token");
        }

    }
}
