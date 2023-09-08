import React, {Component, useState} from "react";
import Grid from "@mui/material/Grid";
import "./LoginPage.css";
import SignIn from "./SignIn/SignIn";
import SignUp from "./SignUp/SignUp";
import {NavLink, Route, Routes} from "react-router-dom";
import ModalWindow from "../../widgets/ModalWindow/ModalWindow";
import MailPage from "./MailPage/MailPage";
import CodeVerificationPage from "./CodeVerificationPage/CodeVerificationPage";
import ChangePasswordPage from "./ChangePasswordPage/ChangePasswordPage";
import AlertDialogSlide from "../../widgets/AlertDialogSlide/AlertDialogSlide";

const LoginPage = (props) => {
 const[isLogin, setIsLogin] = useState(true)
 const[command, setCommand] = useState("")
 const[modalActive, setModalActive] = useState(false)

  let changeLoginState = () => {
    if (isLogin) setIsLogin(false)
    else setIsLogin( true )
  }

    let switchScene = (command) => {
     console.log(command)
      switch (command) {
        case 'mail':
          return <MailPage setCommand={setCommand}
          />
        case 'code':
          return <CodeVerificationPage setCommand={setCommand}
          />
       case 'change':
          return <ChangePasswordPage setCommand={setCommand}
          />
      }
    }


    function startEvent() {
     setModalActive(true)
        switchScene('mail')
    }

    return (
      <Grid container>
        <Grid item xs={3}></Grid>
        <Grid item xs={6}>
          <div className="login-page__main">
            <div>
              {/*<img src={inst_image} width="454px" alt="" />*/}
            </div>
            <div>
              <div className="login-page__right-component">
                <span className="login-page__logo">КИНОПРОКАТ</span>
                <div className="login-page__sign-in">
                  <Routes>
                    <Route index path={'/login'} element={<SignIn/>}/>
                    <Route path={'/register'} element={<SignUp/>}/>
                  </Routes>

                  <div className="login-page__ordiv">
                    <div className="login-page__divider"></div>
                    <div className="login-page__divider-text">ИЛИ</div>
                    <div className="login-page__divider"></div>
                  </div>
                  <div className="login-page__google">
                    {/*<button className="google__google-btn" type="button">*/}
                    {/*  <img src={go} alt="" /> Продолжить с Google*/}
                    {/*</button>*/}
                  </div>
                  <div className="login-page__forgot-password">
                    <button onClick={() => startEvent()}>Забыл пароль?</button>
                  </div>
                </div>
              </div>

              <div className="login-page__signup-option">
                {isLogin ? (
                  <div className="login-page__sign-in-prop">
                    <span>
                      Ты ещё не с нами?{" "}
                      <NavLink to={'register'} onClick={() => changeLoginState()}>
                        Регистрируйся!
                      </NavLink>

                    </span>
                  </div>
                ) : (
                  <div className="login-page__sign-up-prop">
                    Есть регистрация?{" "}
                    <NavLink to={'login'} onClick={() => changeLoginState()}>
                      Входи!
                    </NavLink>

                  </div>
                )}
              </div>
            </div>
          </div>
        </Grid>
        <Grid item xs={3}></Grid>
          <AlertDialogSlide open={modalActive}
                            setOpen={setModalActive}
                            setCommand={setCommand}>
              <ChangePasswordPage/>
          </AlertDialogSlide>
      </Grid>
    );

}

export default LoginPage;
