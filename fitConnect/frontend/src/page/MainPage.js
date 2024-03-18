// MainPage.js
import React from 'react';
import NavbarComponent from '../global/Navbar';

const MainPage = () => {
  return (
      <div>
        <NavbarComponent />
        <div className="container">
          <div className="row align-items-center" style={{ height: '80vh' }}>
            <div className="col-md-6 mx-auto text-center">
              <h1 className="mb-4">FitConnect에 오신걸 환영합니다.</h1>
              <p className="lead mb-5">같이 운동할 친구와 선생님을 구해보세요.</p>
            </div>
          </div>
        </div>
      </div>
  );
};

export default MainPage;
