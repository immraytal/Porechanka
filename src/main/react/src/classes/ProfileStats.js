import axios from "axios";
import moment from "moment";
import React, { useState, useEffect } from "react";
import { useHistory } from "react-router";
import { Link } from "react-router-dom";
import { PureComponent } from "react";
import {
  LineChart,
  Line,
  XAxis,
  YAxis,
  CartesianGrid,
  Tooltip,
  Legend,
} from "recharts";

function ProfileStats() {
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

  const data = [
    {
      year: "2000",
      Одежда: 4231,
      Авто: 2300,
      Литература: 1234,
      Творчество: 4123,
      sum: 6400,
    },
    {
      year: "2001",
      Одежда: 5012,
      Авто: 1008,
      Творчество: 2123,
      Литература: 1234,
      sum: 4398,
    },
    {
      year: "2002",
      Одежда: 5008,
      Авто: 1908,
      Литература: 2000,
      Творчество: 2923,
      sum: 6398,
    },
    {
      year: "2003",
      Одежда: 500,
      Авто: 2398,
      Творчество: 5123,
      Литература: 5534,
      sum: 6398,
    },
    {
      year: "2004",
      Одежда: 5000,
      Авто: 3298,
      Творчество: 3123,
      Литература: 5234,
      sum: 6398,
    },
    {
      year: "2005",
      Творчество: 6123,
      Одежда: 5230,
      Литература: 4811,
      Авто: 2398,
      sum: 6398,
    },
    {
      year: "2006",
      Одежда: 5100,
      Творчество: 6023,
      Литература: 5034,
      Авто: 2198,
      sum: 6398,
    },
    {
      year: "2007",
      Одежда: 5213,
      Творчество: 6123,
      Авто: 1998,
      Литература: 5234,
      sum: 6398,
    },
    {
      year: "2008",
      Одежда: 5000,
      Авто: 2013,
      Литература: 5144,
      Творчество: 6100,
      sum: 6398,
    },
    {
      year: "2009",
      Одежда: 4700,
      Литература: 1234,
      Творчество: 5500,
      Авто: 1100,
      sum: 6398,
    },
    {
      year: "2010",
      Одежда: 5400,
      Авто: 1298,
      Литература: 2234,
      Творчество: 6150,
      sum: 6398,
    },
    {
      year: "2011",
      Одежда: 5230,
      Авто: 1398,
      Творчество: 6200,
      Литература: 2300,
      sum: 6398,
    },
    {
      year: "2012",
      Одежда: 6210,
      Авто: 1498,
      Творчество: 6130,
      Литература: 2000,
      sum: 6398,
    },
    {
      year: "2013",
      Одежда: 4800,
      Литература: 2234,
      Творчество: 6110,
      Авто: 1500,
      sum: 6398,
    },
    {
      year: "2014",
      Одежда: 4900,
      Авто: 1700,
      Творчество: 6100,
      sum: 6398,
      Литература: 2514,
    },
    {
      year: "2015",
      Одежда: 4700,
      Авто: 1898,
      Творчество: 6200,
      Литература: 2024,
      sum: 6398,
    },
    {
      year: "2016",
      Одежда: 5000,
      Авто: 2000,
      Литература: 2200,
      Творчество: 6150,
      sum: 6398,
    },
    {
      year: "2017",
      Одежда: 5100,
      Литература: 3004,
      Творчество: 6250,
      Авто: 2098,
      sum: 6398,
    },
    {
      year: "2018",
      Одежда: 5141,
      Авто: 2158,
      Литература: 3134,
      Творчество: 6400,
      sum: 6398,
    },
    {
      year: "2019",
      Одежда: 4912,
      Авто: 2298,
      Творчество: 6600,
      sum: 6398,
      Литература: 3234,
    },
    {
      year: "2020",
      Одежда: 5000,
      Авто: 2398,
      Творчество: 7000,
      Литература: 3400,
      sum: 6398,
    },
  ];

  const data2 = [
    {
      year: "сент 2019",
      'Кроссовки Louis Vuitton': 21300,
      'Renault Laguna рестайлинг': 50123,
      'Опель Астра F по запчастям 1993 год': 20112,
      Творчество: 41230
    },
    {
      year: "окт 2019",
      'Кроссовки Louis Vuitton': 22660,
      'Renault Laguna рестайлинг': 51223,
      'Опель Астра F по запчастям 1993 год': 21612,
      Творчество: 42430
    },
    {
      year: "ноя 2019",
      'Кроссовки Louis Vuitton': 23300,
      'Renault Laguna рестайлинг': 49123,
      'Опель Астра F по запчастям 1993 год': 25112,
      Творчество: 45230
    },
    {
      year: "дек 2019",
      'Кроссовки Louis Vuitton': 25300,
      'Renault Laguna рестайлинг': 50123,
      'Опель Астра F по запчастям 1993 год': 22112,
      Творчество: 43230
    },
    {
      year: "янв 2020",
      'Кроссовки Louis Vuitton': 26300,
      'Renault Laguna рестайлинг': 52123,
      'Опель Астра F по запчастям 1993 год': 19112,
      Творчество: 40230
    },
    {
      year: "фев 2020",
      'Кроссовки Louis Vuitton': 27300,
      'Renault Laguna рестайлинг': 55123,
      'Опель Астра F по запчастям 1993 год': 21112,
      Творчество: 42230
    },
    {
      year: "мар 2020",
      'Кроссовки Louis Vuitton': 26300,
      'Renault Laguna рестайлинг': 54123,
      'Опель Астра F по запчастям 1993 год': 22112,
      Творчество: 43230
    },
    {
      year: "апр 2020",
      'Кроссовки Louis Vuitton': 22122,
      'Renault Laguna рестайлинг': 55335,
      'Опель Астра F по запчастям 1993 год': 23552,
      Творчество: 41119
    },
    {
      year: "май 2020",
      'Кроссовки Louis Vuitton': 26300,
      'Renault Laguna рестайлинг': 60123,
      'Опель Астра F по запчастям 1993 год': 24112,
      Творчество: 47230
    },
    {
      year: "июн 2009",
      'Кроссовки Louis Vuitton': 28300,
      'Renault Laguna рестайлинг': 61123,
      'Опель Астра F по запчастям 1993 год': 20112,
      Творчество: 44230
    },
    {
      year: "июл 2020",
      'Кроссовки Louis Vuitton': 31300,
      'Renault Laguna рестайлинг': 60123,
      'Опель Астра F по запчастям 1993 год': 21112,
      Творчество: 42230
    },
    {
      year: "авг 2020",
      'Кроссовки Louis Vuitton': 27300,
      'Renault Laguna рестайлинг': 55123,
      'Опель Астра F по запчастям 1993 год': 20112,
      Творчество: 40230
    },
    {
      year: "сент 2020",
      'Кроссовки Louis Vuitton': 24300,
      'Renault Laguna рестайлинг': 52123,
      'Опель Астра F по запчастям 1993 год': 21112,
      Творчество: 42333
    },
    {
      year: "окт 2020",
      'Кроссовки Louis Vuitton': 23300,
      'Renault Laguna рестайлинг': 49123,
      'Опель Астра F по запчастям 1993 год': 17612,
      Творчество: 40230
    },
    {
      year: "ноя 2020",
      'Кроссовки Louis Vuitton': 24300,
      'Renault Laguna рестайлинг': 50123,
      'Опель Астра F по запчастям 1993 год': 20112,
      Творчество: 41230
    },
    {
      year: "дек 2020",
      'Кроссовки Louis Vuitton': 23331,
      'Renault Laguna рестайлинг': 52222,
      'Опель Астра F по запчастям 1993 год': 21111,
      Творчество: 40000
    },
    {
      year: "янв 2020",
      'Кроссовки Louis Vuitton': 22300,
      'Renault Laguna рестайлинг': 51123,
      'Опель Астра F по запчастям 1993 год': 19112,
      Творчество: 38230
    },
    {
      year: "фев 2020",
      'Кроссовки Louis Vuitton': 19300,
      'Renault Laguna рестайлинг': 49123,
      'Опель Астра F по запчастям 1993 год': 21112,
      Творчество: 42230
    },
    {
      year: "мар 2020",
      'Кроссовки Louis Vuitton': 21300,
      'Renault Laguna рестайлинг': 50123,
      'Опель Астра F по запчастям 1993 год': 20112,
      Творчество: 41230
    },
    {
      year: "апр 2020",
      'Кроссовки Louis Vuitton': 21312,
      'Renault Laguna рестайлинг': 51133,
      'Опель Астра F по запчастям 1993 год': 20009,
      Творчество: 42777
    },
    {
      year: "май 2020",
      'Кроссовки Louis Vuitton': 23710,
      'Renault Laguna рестайлинг': 50123,
      'Опель Астра F по запчастям 1993 год': 21212,
      Творчество: 41230
    },
  ];

  return (
    <div>
      <h1 style={{textAlign:'center', marginTop:'10px', marginBottom:'20px'}}>Статистика годовой выручки по категориям объявлений</h1>
      <LineChart
       width={1850}
       height={700}
        data={data}
        margin={{
          top: 5,
          right: 30,
          left: 20,
          bottom: 10,
        }}
      >
        <CartesianGrid strokeDasharray="3 3" />
        <XAxis label={{ value: 'Год', position: 'insideBottomRight', offset: '-10'}} dataKey="year" />
        <YAxis label={{ value: 'Сумма', angle: -90, offset: '-10' }} domain={[0, "dataMax + 600"]} dataKey="sum" />
        <Tooltip />
        <Legend />
        <Line type="monotone" dataKey="Авто" stroke="#8884d8" />
        <Line type="monotone" dataKey="Творчество" stroke="#01231f" />
        <Line type="monotone" dataKey="Литература" stroke="#000fff" />
        <Line type="monotone" dataKey="Одежда" stroke="#82ca9d" />
      </LineChart>
      <h1 style={{textAlign:'center', marginTop:'20px', marginBottom:'20px'}}>Статистика просмотров объявлений по месяцам</h1>
      <LineChart
        width={1850}
        height={700}
        data={data2}
        margin={{
          top: 5,
          right: 30,
          left: 20,
          bottom: 15,
        }}
      >
        <CartesianGrid strokeDasharray="3 3" />
        <XAxis label={{ value: 'Дата', position: 'insideBottomRight', offset: '-10'}} dataKey="year" />
        <YAxis label={{ value: 'Просмотры', angle: -90, offset: '-10' }}  domain={[0, "dataMax + 3000"]} />
        <Tooltip />
        <Legend />
        <Line type="monotone" dataKey="Renault Laguna рестайлинг" stroke="#8884d8" />
        <Line type="monotone" dataKey="Кроссовки Louis Vuitton" stroke="#01231f" />
        <Line type="monotone" dataKey="Опель Астра F по запчастям 1993 год" stroke="#000fff" />
      </LineChart>
    </div>
  );
}

export default ProfileStats;
