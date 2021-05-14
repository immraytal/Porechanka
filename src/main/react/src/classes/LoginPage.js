import { Field, Form, Formik } from "formik";
import * as Yup from "yup";
import React, { useState, useEffect } from "react";
import axios from "axios";
import { useHistory } from "react-router";

function LoginPage() {
  const loginSchema = Yup.object().shape({
    login: Yup.string().required("Обязательное"),
    password: Yup.string().required("Обязательное"),
  });

  const history = useHistory();
  const [value, setValue] = useState();
  useEffect(() => {
    const token = localStorage.getItem("token");
    if (token) {
      history.push("/");
    }
  }, []);

  const sumbitLogin = async (values) => {
    const data = await axios
      .post("http://localhost:8080/login", {
        login: values.login,
        password: values.password,
      })
      .catch((err) => {
        if (err.response.status === 401) {
          alert("Неправильный логин или пароль");
        }
      });
    localStorage.setItem("token", data.headers["authorization"]);
    window.location.reload();
  };

  return (
    <div className="login-form">
      <Formik
        initialValues={{
          login: "",
          password: "",
        }}
        validationSchema={loginSchema}
        onSubmit={sumbitLogin}
      >
        {({ errors, touched }) => (
          <Form>
            <div className="form-block">
              <label className="form-label">Логин</label>
              <Field className="form-control" id="login" name="login" />
              {touched.login && errors.login && (
                <label className="error-msg">{errors.login}</label>
              )}
            </div>
            <div className="form-block">
              <label className="form-label">Пароль</label>
              <Field
                className="form-control form-control-error"
                id="password"
                name="password"
                type="password"
              />
              {touched.password && errors.password && (
                <label className="error-msg">{errors.password}</label>
              )}
            </div>
            <button className="btn btn-primary form-block" type="submit">
              Войти
            </button>
          </Form>
        )}
      </Formik>
    </div>
  );
}

export default LoginPage;
