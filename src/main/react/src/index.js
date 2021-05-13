import React from 'react';
import ReactDOM from 'react-dom';
import App from './App';
import Header from './Header';
import './index.css';
import reportWebVitals from './reportWebVitals';

document.title = 'Porechanka';
ReactDOM.render(
   <App />,
  document.getElementById('root')
);

ReactDOM.render(
  <Header />,
document.getElementById('header')
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
