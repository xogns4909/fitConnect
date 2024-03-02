import React from 'react';
import GoogleLoginButton from '../components/GoogleLoginButton';
import NavbarComponent from '../components/Navbar';
import { Container, Row, Col, Card } from 'react-bootstrap';

const LoginPage = () => {
  return (
      <>
        <NavbarComponent />
        <Container className="d-flex justify-content-center align-items-center" style={{ minHeight: "100vh" }}>
          <Row>
            <Col>
              <Card className="text-center" style={{ width: '18rem' }}>
                <Card.Body>
                  <Card.Title>로그인</Card.Title>
                  <Card.Text>
                    FitConnect에 오신 것을 환영합니다! 계속하려면 로그인하세요.
                  </Card.Text>
                  <GoogleLoginButton />
                </Card.Body>
              </Card>
            </Col>
          </Row>
        </Container>
      </>
  );
};

export default LoginPage;
