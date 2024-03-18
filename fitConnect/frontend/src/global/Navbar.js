import React from 'react';
import { Link } from 'react-router-dom';
import { Navbar, Nav, NavDropdown } from 'react-bootstrap';
import { useAuth } from "./AuthContext";
import { useNavigate } from 'react-router-dom';


const NavbarComponent = () => {
  const { isLoggedIn, logout } = useAuth();

  return (
      <Navbar bg="light" expand="lg">
        <Navbar.Brand as={Link} to="/">FitConnect</Navbar.Brand>
        <Navbar.Toggle aria-controls="basic-navbar-nav" />
        <Navbar.Collapse id="basic-navbar-nav">
          <Nav className="ml-auto">
            {isLoggedIn ? (
                <>
                  <Nav.Link as={Link} to="/new-post">새글쓰기</Nav.Link>
                  <NavDropdown title="메뉴" id="basic-nav-dropdown">
                    <NavDropdown.Item as={Link} to="/mypage">마이페이지</NavDropdown.Item>
                    <NavDropdown.Item as={Link} to="/chatRooms">채팅</NavDropdown.Item>
                    <NavDropdown.Item as={Link} to="/event">게시글</NavDropdown.Item>
                    <NavDropdown.Item onClick={logout}>로그아웃</NavDropdown.Item>
                  </NavDropdown>
                </>
            ) : (
                <Nav.Link as={Link} to="/login">로그인</Nav.Link>
            )}
          </Nav>
        </Navbar.Collapse>
      </Navbar>
  );
};

export default NavbarComponent;
