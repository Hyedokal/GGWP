import './style.css';
import { useUserStore } from 'stores';
import {useState} from "react";
import {useNavigate} from "react-router-dom";


//          component: 유저 페이지          //
export default function User() {


  // 유저 정보 가져오기
    const { user, setUser } = useUserStore();
  const { email, lolNickname, tag, userRole } = user || {};

    // 본인 여부 상태
    const [isMyPage, setMyPage] = useState<boolean>(false);

    //          function: 네비게이트 함수          //
    const navigator = useNavigate();




  return (
      <div className="my-page">
        <h1>My Page</h1>
        <p>Email: {email}</p>
        <p>LoL Nickname: {lolNickname}</p>
        <p>Tag: {tag}</p>
        <p>Role: {userRole}</p>
      </div>
  );


}
