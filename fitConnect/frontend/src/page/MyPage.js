import React from 'react';
import { Container, Nav, Row, Col } from 'react-bootstrap';
import { Outlet, Link, useLocation, Navigate } from 'react-router-dom';
import NavbarComponent from '../global/Navbar';

const MyPage = () => {
  const location = useLocation();
  const currentTab = location.pathname.split("/")[2];

  return (
      <>
        <NavbarComponent />
        <Container className="my-page mt-3">
          <h2>마이페이지</h2>
          <Row>
            <Col sm={3}>
              <Nav variant="pills" className="flex-column mt-3">
                <Nav.Item>
                  <Nav.Link as={Link} to="user-info">유저 정보</Nav.Link>
                </Nav.Item>
                <Nav.Item>
                  <Nav.Link as={Link} to="review">리뷰</Nav.Link>
                </Nav.Item>
                <Nav.Item>
                  <Nav.Link as={Link} to="registered-events">등록한 이벤트</Nav.Link>
                </Nav.Item>
                <Nav.Item>
                  <Nav.Link as={Link} to="my-events">내 이벤트</Nav.Link>
                </Nav.Item>
              </Nav>
            </Col>
            <Col sm={9}>
              {!currentTab && <Navigate to="user-info" replace />}
              <Outlet />
            </Col>
          </Row>
        </Container>
      </>
  );
};

export default MyPage;
