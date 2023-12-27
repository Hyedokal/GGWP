import {Outlet, useLocation} from "react-router-dom";
import Header from 'layouts/Header'
import Footer from 'layouts/Footer'
import React from 'react'
import {AUTH_PATH} from 'constant';
import './style.css';
import * as diagnostics_channel from "diagnostics_channel";
import {useCookies} from "react-cookie";

//          component: 메인 레이아웃          //
export default function Container() {


    //          render: 메인 레이아웃 렌더링          //
  return (
    <div>
        <Header/>
        <div id='Main'> <Outlet/></div>


         <Footer/>
    </div>
  )
}
