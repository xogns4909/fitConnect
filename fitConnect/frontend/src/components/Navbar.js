import React from 'react';
import { Link } from 'react-router-dom';
import { Navbar, Nav, NavDropdown } from 'react-bootstrap';
import { useAuth } from "../global/AuthContext"; // 경로는 실제 프로젝트 구조에 맞게 조정해야 합니다.
import GoogleLoginButton from './GoogleLoginButton';

const NavbarComponent = () => {
  const { isLoggedIn, logout } = useAuth();

  return (
      <Navbar bg="light" expand="lg">
        <Navbar.Brand as={Link} to="/">FitConnect</Navbar.Brand>
        <Nav className="ml-auto">
          {isLoggedIn ? (
              <>
                <Nav.Link as={Link} to="/new-post">새글쓰기</Nav.Link>
                <NavDropdown title="메뉴" id="basic-nav-dropdown">
                  <NavDropdown.Item as={Link} to="/mypage">마이페이지</NavDropdown.Item>
                  <NavDropdown.Item as={Link} to="/chat">채팅</NavDropdown.Item>
                  <NavDropdown.Item onClick={logout}>로그아웃</NavDropdown.Item>
                </NavDropdown>
              </>
          ) : (
              <GoogleLoginButton />
          )}
        </Nav>
      </Navbar>
  );
};

export default NavbarComponent;