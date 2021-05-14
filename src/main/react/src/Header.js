import axios from "axios";
import React, { useEffect, useState } from "react";
import { Navbar, NavDropdown } from "react-bootstrap";
import { useHistory } from "react-router";
import LogoProfile from "./classes/LogoProfile";

export default function Header() {
  const history = useHistory();

  const [data, setData] = useState({
    id: 3,
    login: "",
    email: "",
    phoneNumber: "",
    firstName: "",
  });

  const pushToMain = () => {
    history.push("/");
  };

  const pushToLogin = () => {
    history.push("/login");
  };

  const logout = () => {
    localStorage.clear();
    history.go();
  };

  const pushToProfile = () => {
    history.push("/profile");
  };

  const sumbitLogin = async (values) => {
    const token = localStorage.getItem("token");
    if (token) {
      await axios
        .get("http://localhost:8080/profile", {
          headers: { Authorization: token },
        })
        .then((body) => setData(body.data));
    }
  };

  useEffect(() => {
    const token = localStorage.getItem("token");
    if (token) {
      sumbitLogin();
    }
  }, []);

  return (
    <div>
      <Navbar className="header" bg="light" expand="lg">
        <Navbar.Brand className="header-logo" onClick={pushToMain}>
          Porechanka
        </Navbar.Brand>
        <Navbar.Toggle aria-controls="basic-navbar-nav" />
        {localStorage.getItem("token") ? (
          <Navbar.Collapse className="justify-content-end">
            <NavDropdown title={data.firstName} id="basic-nav-dropdown ">
              <NavDropdown.Item onClick={pushToProfile}>Мой аккаунт</NavDropdown.Item>
              <NavDropdown.Divider />
              <NavDropdown.Item onClick={logout}>Выход</NavDropdown.Item>
            </NavDropdown>
          </Navbar.Collapse>
        ) : <div className="login-btn" onClick={pushToLogin}>Login</div> }
      </Navbar>
    </div>
  );
}
