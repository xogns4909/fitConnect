import React from 'react';
import { Container, Tab, Nav, Row, Col } from 'react-bootstrap';
import ReviewsList from '../components/ReviewList';
import RegisteredEventsList from '../components/RegisteredEventsList';
import MyEventsList from '../components/MyEventsList';
import NavbarComponent from '../components/Navbar';
import UserInfo from "../components/UserInfo";
import '../style/Mypage.css';

const MyPage = () => {
  return (
      <>
        <NavbarComponent />
        <Container className="my-page mt-3">
          <h2>마이페이지</h2>
          <Tab.Container defaultActiveKey="userInfo">
            <Row>
              <Col sm={3}>
                <Nav variant="pills" className="flex-column mt-3">
                  <Nav.Item>
                    <Nav.Link eventKey="userInfo">유저 정보</Nav.Link>
                  </Nav.Item>
                  <Nav.Item>
                    <Nav.Link eventKey="reviews">리뷰</Nav.Link>
                  </Nav.Item>
                  <Nav.Item>
                    <Nav.Link eventKey="registeredEvents">등록한 이벤트</Nav.Link>
                  </Nav.Item>
                  <Nav.Item>
                    <Nav.Link eventKey="myEvents">내 이벤트</Nav.Link>
                  </Nav.Item>
                </Nav>
              </Col>
              <Col sm={9}>
                <Tab.Content>
                  <Tab.Pane eventKey="userInfo">
                    <UserInfo />
                  </Tab.Pane>
                  <Tab.Pane eventKey="reviews">
                    <ReviewsList />
                  </Tab.Pane>
                  <Tab.Pane eventKey="registeredEvents">
                    <RegisteredEventsList />
                  </Tab.Pane>
                  <Tab.Pane eventKey="myEvents">
                    <MyEventsList />
                  </Tab.Pane>
                </Tab.Content>
              </Col>
            </Row>
          </Tab.Container>
        </Container>
      </>
  );
};

export default MyPage;
