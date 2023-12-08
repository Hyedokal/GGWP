import React from 'react';
import './style.css';

//          component: 푸터 컴포넌트          //
export default function Footer() {

  //          event handler: 인스타 아이콘 버튼 클릭 이벤트 처리         //
  const onInstaIconClickHandler = () => {
    window.location.href = 'https://www.instagram.com';
  }
  //          event handler: 네이버 블로그 아이콘 버튼 클릭 이벤트 처리         //
  const onNaverBlogIconClickHandler = () => {
    window.open('https://blog.naver.com');
  }

  //          render: 푸터 컴포넌트 렌더링         //
  return (
    <div id='footer'>
      <div className='footer-top'>
        <div className='footer-logo-box'>
          <div className='footer-logo-icon-box'>
            <div className='logo-light-icon'></div>
          </div>
          <div className='footer-logo-text'>{'GGWP'}</div>
        </div>
        <div className='footer-link-box'>
          <div className='email-link'>{'email@email.com'}</div>
          <div className='icon-button' onClick={onInstaIconClickHandler}>
            <div className='insta-icon'></div>
          </div>
          <div className='icon-button' onClick={onNaverBlogIconClickHandler}>
            <div className='naver-blog-icon'></div>
          </div>
        </div>
      </div>
      <div className='footer-bottom'>
        <div className='footer-copyright'>{'Copyright ⓒ 2022 GGWP. All Right Reserved.'}</div>
      </div>
    </div>
  )
}
