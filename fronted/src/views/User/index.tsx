import React, { ChangeEvent, useEffect, useRef, useState } from 'react';
import './style.css';
import { useNavigate, useParams } from 'react-router-dom';
import { useUserStore } from 'stores';
import { AUTH_PATH, MAIN_PATH, USER_PATH } from 'constant';
import {  getSignInUserRequest, getUserRequest } from 'apis';
import { GetSignInUserResponseDto, GetUserResponseDto } from 'apis/dto/response/user';
import ResponseDto from 'apis/dto/response';
import { useCookies } from 'react-cookie';

//          component: 유저 페이지          //
export default function User() {

  //          state: 로그인 유저 정보 상태           //
  const { user, setUser } = useUserStore();





  // 유저 이메일  출력
    console.log(user?.email);
  console.log(user);


  return <div>User Page
    <div>{user?.email}</div>
     <div>{user?.tag}</div>



  </div>;


}
