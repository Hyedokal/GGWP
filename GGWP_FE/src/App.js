import axios from "axios"

import{useEffect} from "react";
import { useNavigate } from 'react-router-dom';
// import './styles.css'



// function App() {
//     const API_KEY = "6c01a4160a5e8472a65359304bb6d1f0";
//     const REDIRECT_URI = 'http://localhost:3000/oauth';
//     const AUTH_URI = `https://kauth.kakao.com/oauth/authorize?client_id=${API_KEY}&redirect_uri=${REDIRECT_URI}&response_type=code`;
//     //인가코드 받아오기
//     const code = new URL(window.location.href).searchParams.get('code')
    //로그인 성공시 Mypage로 이동시키기 위해 useNavigate 사용
    // const navigate = useNavigate();
    // useEffect(() => {
    //     //백엔드로부터 인가코드 넘기고 jwt 토큰 받기
    //     (async () => {
    //         try {
    //             const res = await axios
    //                 //백엔드 주소 뒤에 인가코드 붙여서 GET 설정
    //                 //백엔드는 이 주소를 통해 뒤에 붙여져 있는 인가코드를 전달 받게 된다.
    //                 .get(`api/code=${code}`);
    //             const token = res.headers.authorization;
    //             window.localStorage.setItem('token', token);
    //             navigate('/main');
    //         } catch (e) {
    //             console.error(e);
    //             navigate('/main');
    //         }
    //     })();
    // }, [code, navigate]);


    // return (
    //     <div className="App">
    //         <header className="App-header">
    //
    //             <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'center', justifyContent: 'flex-start', height: '100vh' }}>
    //                <a href={AUTH_URI}>
    //                    <div className="background-rectangle" style={{ backgroundColor: '#DFF9F7', width: '1000px', height: '472px', position: 'absolute', top: '0', left: '0', zIndex:'0' }} />
    //                    <button style={{ padding: '10px', fontSize: '16px', cursor: 'pointer' ,backgroundColor: 'yellow',marginTop: '200px',marginLeft:'200px',zIndex:'2'}}>
    //                        <img src="v1_56.png" alt="Kakao Logo" style={{ width: '20px', height: '20px', marginRight: '10px' }} />
    //
    //
    //                    카카오로 시작하기
    //                    </button>
    //
    //                </a>
    //             </div>
    //         </header>
    //     </div>
    // );
//     return (
//          <div className="App">
//             <header className="App-header">
//                 <div style={{ position: 'relative' }}>
//
//                         <div className="background-rectangle" style={{ backgroundColor: '#DFF9F7', width: '1200px', height: '400px', position: 'absolute', top: '100px', left: '180px', zIndex:'1' }} />
//                         <a href={AUTH_URI}>
//                         <button style={{ padding: '10px', fontSize: '16px', cursor: 'pointer' ,backgroundColor: 'yellow', marginTop: '200px', marginLeft: '900px', position: 'relative', zIndex: '2' }}>
//                             <img src="/images/v1_56.png" alt="Kakao Logo" style={{ width: '20px', height: '20px', marginRight: '10px' }} />
//                             카카오로 시작하기
//                         </button>
//                     </a>
//                     <img src="/images/v139.png" alt="" style={{ position: 'absolute', width: '300px',height:'300px',top: '100px', left: '300px', zIndex: '2' }} />
//                 </div>
//             </header>
//         </div>
//     );
// }
// import Login from "./Login";
// import './App.css'
//
// function App() {
//     return (
//         <div>
//             <Login />
//         </div>
//     );
// }

// YourComponent.js (예시 파일명)
// import React from 'react';
// import './styles.css'; // 생성한 CSS 파일을 import
//
// function YourComponent() {
//     return (
//         <div className="v139">
//             {/* 내용 추가 */}
//         </div>
//     );
// }
//
// export default YourComponent;
//



// 컴포넌트화 한것
import React from 'react';
import { BrowserRouter as Router } from 'react-router-dom';

import BackgroundRectangle from './BackgroundRectangle';
import KakaoButton from './KakaoButton';
import KakaoImage from './KakaoImage';
 //import KakaoImageTop from './KakaoImageTop';
import TextOnRectangle from './TextOnRectangle';
 //import MatchButton from './MatchButton';
 //import SearchButton from './SearchButton';
 //import MyInfoButton from './MyInfoButton';
import AdminLoginButton from './AdminLoginButton';
import Header from './Header';
//import Log from './Log';
import MyPage from './MyPage';
//import Header2 from './Header2';
function App() {
    const API_KEY = "6c01a4160a5e8472a65359304bb6d1f0";
    const REDIRECT_URI = 'http://localhost:3000/oauth';
    const AUTH_URI = `https://kauth.kakao.com/oauth/authorize?client_id=${API_KEY}&redirect_uri=${REDIRECT_URI}&response_type=code`;

    return (
        <Router>
        <div className="App" >
            <header className="App-header">
                <div >


                  <Header/>
                    <MyPage/>


                    <div >


                    </div>

                </div>

            </header>
        </div>
        </Router>
    );
}

// export default App;

  export default App;



