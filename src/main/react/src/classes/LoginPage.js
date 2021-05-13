import { Field, Form, Formik } from "formik";
import * as Yup from "yup";
import React, { useState, useEffect } from "react";

function LoginPage() {
  const loginSchema = Yup.object().shape({
    login: Yup.string().required("Обязательное"),
    password: Yup.string().required("Обязательное"),
  });

  const sumbitLogin = async (values) => {
    console.log(values);
    const data = await fetch("http://localhost:8080/auth", {
      method: "POST",
      body: {
        username: "root",
        password: "rootroot",
      },
      //   headers: {
      //     Vary: "Origin",
      //     Vary: "Access-Control-Request-Method",
      //     Vary: "Access-Control-Request-Headers",
      //     "X-Content-Type-Options": "nosniff",
      //     "X-XSS-Protection": "1; mode=block",
      //     "Cache-Control": "no-cache, no-store, max-age=0, must-revalidate",
      //     Pragma: "no-cache",
      //     Expires: "0",
      //     "X-Frame-Options": "DENY",
      //     "Content-Type": "application/json",
      //     "Transfer-Encoding": "chunked",
      //     "Keep-Alive": "timeout=60",
      //     Connection: "keep-alive",
      //   },
    });
    console.log(data);
  };

  return (
    <div>
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
