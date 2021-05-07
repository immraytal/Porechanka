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

  function getDecription(str) {
    let decr = str.slice(0, 128);
    if (str.length > decr.length) {
      return decr.concat("...");
    } else {
      return decr;
    }
  }

  return (
    <ul className="advert-items-ul">
      {adverts.map((a) => (
        <li className="advert-items-li" key={a.id}>
          <div className="advert-header">
            <div className="advert-name">{a.title}</div>
            <div className="advert-price">
              {a.price > 0 ? a.price + " BYN" : "Цена договорная"}
            </div>
          </div>
          <div className="advert-body">
            <div className="advert-body-description">
              {getDecription(String(a.body))}
            </div>
            <div className="advert-time">
              {a.openTime} {a.openDate}
            </div>
          </div>
        </li>
      ))}
    </ul>
  );
}

export default Advert;
