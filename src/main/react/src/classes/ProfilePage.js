import axios from "axios";
import moment from "moment";
import React, { useState, useEffect } from "react";
import { useHistory } from "react-router";
import { Link } from "react-router-dom";

function ProfilePage() {
  //const [token, setToken] = useState('')
  const history = useHistory();  
  const [seller, setSeller] = useState({
    id: 0,
    login: "",
    email: "",
    phoneNumber: "",
    firstName: "",
    surName: "",
    birth: "",
    age: 0,
    sex: "",
    rating: 0,
    userStatus: "",
    createdAccountDate: "",
    createdAccountTime: "",
  });

  const [adverts, setAdverts] = useState([
    {
      id: 0,
      title: "",
      body: "",
      price: 0,
      advertStatus: "",
      photoUrl: "",
      openTime: "",
      openDate: "",
      category: {
        id: 0,
        name: "123",
        description: "",
      },
      closeDate: null,
      closeTime: null,
      seller: {
        id: 0,
        login: "",
        email: "",
        phoneNumber: "",
        firstName: "",
        surName: "",
        birth: "",
        age: 0,
        sex: "",
        rating: 0,
        userStatus: "",
        createdAccountDate: "",
        createdAccountTime: "",
      },
      buyer: null,
      paid: false,
    },
  ]);

  const logout = () => {
    localStorage.clear();
    history.go();
  };

  useEffect(() => {
    //setToken(localStorage.getItem("token"));
    const token = localStorage.getItem("token");
    if (token) {
      axios
        .get("http://localhost:8080/profile", {
          headers: { Authorization: token },
        })
        .then((body) => setSeller(body.data));
    } else {
      history.push("/login");
    }
    axios
      .get("http://localhost:8080/profile/adverts", {
        headers: { Authorization: token },
      })
      .then((body) => setAdverts(body.data));
  }, []);

  function getPhoto(photoUrl) {
    return <img src={photoUrl} alt="new" width="200" height="125" />;
  }

  function getPrice(price) {
    if (price === 0) {
      return "Бесплатно";
    } else if (price < 0) {
      return "Цена договорная";
    } else {
      return price + " BYN";
    }
  }

  function getDateTime(openDate, openTime) {
    var time = moment(openDate.concat(" ").concat(openTime));
    moment.locale("ru");
    return time.format("llll");
  }

  function getTitle(advert) {
    if (advert.advertStatus === 'CLOSED') {
      return (<div style={{textDecoration:'line-through'}}>{advert.title}</div>)
    } else if (advert.advertStatus === 'OPENED') {
      return (advert.title)
    }

  }

  return (
    <div className="profile-body">
      <div className="profile-adverts-block">
        <ul className="advert-items-ul" style={{ marginTop: "50px" }}>
          {adverts.map((a) => (
            <Link
              key={a.id}
              style={{ textDecoration: "none", color: "black" }}
              to={`/adverts/${a.id}`}
            >
              <li>
                <div className="advert-items-li">
                  <div className="advert-photo-li">{getPhoto(a.photoUrl)}</div>
                  <div className="advert-base-info-li">
                    <div className="advert-base-info-header-li">
                      <div className="advert-base-info-header-title-li">
                        {getTitle(a)}
                      </div>
                      <Link
                        style={{ textDecoration: "none", color: "black" }}
                        to={`/adverts?category=${a.category.id}`}
                      >
                        <div className="advert-base-info-header-category-li">
                          {a.category.name}
                        </div>
                      </Link>
                    </div>
                    <div className="advert-base-info-body-li">
                      <div className="advert-base-info-body-price-li">
                        {getPrice(a.price)}
                      </div>
                      <div className="advert-base-info-body-date-li">
                        {getDateTime(a.openDate, a.openTime)}
                      </div>
                    </div>
                  </div>
                </div>
              </li>
            </Link>
          ))}
        </ul>
      </div>
      <div className="profile-personal-block">
        <div className="profile-personal-info">
          <div className="profile-info">
            <div>
              {seller.firstName} {seller.surName}
            </div>
            <div>Рейтинг: {seller.rating}</div>
            <div>
              <div>Телефон: +375 {seller.phoneNumber}</div>
            </div>
            <div>
              <div>E-mail: {seller.email}</div>
            </div>
          </div>
        </div>
        <div className="profile-personal-tools-block">
          <Link
            style={{ textDecoration: "none", color: "black" }}
            to={`/profile/stats`}
          >
            <div className="profile-personal-tools-block-button">
              Статистика
            </div>
          </Link>
          <div className="profile-personal-tools-block-button">Настройки</div>

          <div className="profile-personal-tools-block-button">Поддержка</div>
          <div
            className="profile-personal-tools-block-button"
            style={{ backgroundColor: "rgba(209, 7, 7, 0.952)", color: "#fff" }}
            onClick={logout}
          >
            Выйти из аккаунта
          </div>
        </div>
      </div>
    </div>
  );
}

export default ProfilePage;
