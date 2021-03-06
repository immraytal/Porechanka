import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import moment from 'moment/min/moment-with-locales';

function AdvertsList() {
  const [adverts, setAdverts] = useState([]);

  console.log('advertList')

  useEffect(() => {
    fetch("http://localhost:8080/adverts?size=10")
      .then((response) => response.json())
      .then((adverts) => {
        setAdverts(adverts);
      });
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

  return (
    <ul className="advert-items-ul">
      {adverts.map((a) => (
        <Link key={a.id} style={{ textDecoration: "none", color: "black"}} to={`/adverts/${a.id}`}>
          <li>
            <div className="advert-items-li">
              <div className="advert-photo-li">{getPhoto(a.photoUrl)}</div>
              <div className="advert-base-info-li">
                <div className="advert-base-info-header-li">
                  <div className="advert-base-info-header-title-li">
                    {a.title}
                  </div>
                  <div className="advert-base-info-header-category-li">
                  <Link key={a.id} style={{ textDecoration: "none", color: "black"}} to={`/adverts?category=${a.category.id}`}>
                    {a.category.name}
                    </Link>
                  </div>
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
  );
}

export default AdvertsList;
