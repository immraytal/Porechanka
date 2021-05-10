import React, { useState, useEffect } from "react";
import moment from 'moment/min/moment-with-locales';

function AdvertInfo(props) {
  const [advert, setAdvert] = useState({
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
      name: "",
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
  });

  const url = "http://localhost:8080/adverts/".concat(props.match.params.advertId);
  useEffect(() => {
    fetch(url)
      .then((response) => response.json())
      .then((data) => {
        console.log(data);
        setAdvert(data);
      });
  }, []);

  function getPhoto(photoUrl) {
    return <img src={photoUrl} />;
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

  function getDateTime(){
    var time = moment(advert.openDate.concat(" ").concat(advert.openTime));
    moment.locale('ru');
    return (time.format("llll"))
  }

  return (
    <div className="body">
      <div className="advert-info-category">{advert.category.name}</div>
      <div className="advert-body-info">
        <div className="advert-body-info-photo-description">
          <div className="advert-body-info-photo-description-photo-place">
            {getPhoto(advert.photoUrl)}
          </div>
          <div className="advert-body-info-photo-description-date">
            {getDateTime()}
          </div>
          <div className="advert-body-info-photo-description-body">
            {advert.body}
          </div>
        </div>
        <div className="advert-body-info-title-price-block">
          <div className="advert-body-info-title-price-three-block">
            <div className="advert-body-info-title-price-title">
              {advert.title}
            </div>
            <div className="advert-body-info-title-price-gps">Гродно</div>
            <div className="advert-body-info-title-price-price">
              {getPrice(advert.price)}
            </div>
          </div>
          <button
            type="button"
            className="advert-body-info-title-price-write-seller-write"
          >
            Написать
          </button>
          <div>
            <button
              type="button"
              className="advert-body-info-title-price-write-seller-call"
            >
              Позвонить
            </button>
          </div>
          <div className="advert-body-info-title-price-seller-info">
            <div className="advert-body-info-title-price-seller-info-name">
              {advert.seller.firstName}{" "}
              {advert.seller.firstName.slice(0, 1).concat(".")}
            </div>
            <div>Рейтинг: {advert.seller.rating}</div>
            <div className="advert-body-info-title-price-seller-info-phone">
              Телефон: &nbsp;
              <div>+375 {advert.seller.phoneNumber}</div>
            </div>
            <div className="advert-body-info-title-price-seller-info-email">
              E-mail: &nbsp;
              <div>{advert.seller.email}</div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default AdvertInfo;
