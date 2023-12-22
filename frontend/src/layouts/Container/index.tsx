import {Outlet, useLocation} from "react-router-dom";
import Header from 'layouts/Header'
import Footer from 'layouts/Footer'
import React from 'react'
import {AUTH_PATH} from 'constant';
import './style.css';
import * as diagnostics_channel from "diagnostics_channel";
import SliderBar from "../Sliderbar";
import {useCookies} from "react-cookie";

//          component: 메인 레이아웃          //
export default function Container() {

    //토큰이 없으면 SlideBar를 보여주지 않는다.
    const [cookies, setCookie] = useCookies(); // state: Cookie state //
    const token = cookies['accessToken'];




    //          render: 메인 레이아웃 렌더링          //
  return (
    <div>
        <Header/>
        <div id='Main'> <Outlet/></div>


        {token && <SliderBar/>}

         <Footer/>
    </div>
  )
}
