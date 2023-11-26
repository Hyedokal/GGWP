
import { FaBell } from "react-icons/fa";
import './App.css';
import React from "react"; // 스타일 파일 임포트
const AdminPage = () => {



return(
    <div>

        <div className="custom-box">
            <p>관리자페이지</p>
        </div>
        <div className="adminlistbox">
            <div className = "header">
            <div>유저명</div>
            <div>생성일</div>
            <div>신고내용</div>
            <div>신고횟수</div>
            </div>
        </div>

        <div> <FaBell className="FaBell" /></div>

    </div>


)

}
export default AdminPage;