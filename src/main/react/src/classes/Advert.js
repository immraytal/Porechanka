import React, { useState, useEffect } from "react";

function Advert() {
  const [adverts, setAdverts] = useState([]);

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
      return ("Бесплатно")
    } else if (price < 0) {
      return ("Цена договорная")
    } else {
      return (price + " BYN")
    }
  }

  return (
    <ul className="advert-items-ul">
      {adverts.map((a) => (
        <li>
          <div className="advert-items-li">
            <div className="advert-photo-li">
              {getPhoto(a.photoUrl)}
            </div>
            <div className="advert-base-info-li">
              <div className="advert-base-info-header-li">
                <div className="advert-base-info-header-title-li">
                  {a.title}
                </div>
                <div className="advert-base-info-header-category-li">
                  {a.category.name}
                </div>
              </div>
              <div className="advert-base-info-body-li">
                <div className="advert-base-info-body-price-li">
                  {getPrice(a.price)}
                </div>
                <div className="advert-base-info-body-date-li">
                  {a.openTime} {a.openDate}
                </div>
              </div>
            </div>
          </div>
        </li>
      ))}
    </ul>
  );
}

export default Advert;
