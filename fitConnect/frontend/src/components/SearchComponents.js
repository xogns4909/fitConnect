// SearchComponent.js
import React from 'react';
import {Form, Button, Row, Col} from 'react-bootstrap';

const SearchComponent = ({setCategory, setCity, setDescription, onSearch}) => {
  return (
      <Form>
        <Row>
          <Col>
            <Form.Control as="select"
                          onChange={(e) => setCategory(e.target.value)}>
              <option>운동 종목</option>
              <option value={"SOCCER"}>축구</option>
              <option value={"BASKETBALL"}>농구</option>
              <option value={"BASEBALL"}>야구</option>
              <option value={"GOLF"}>골프</option>
              <option value={"FITNESS"}>헬스</option>
              <option value={"BOWLING"}>볼링</option>
              <option value={"BILLIARDS"}>당구</option>
            </Form.Control>
          </Col>
          <Col>
            <Form.Control as="select" onChange={(e) => setCity(e.target.value)}>
              <option>지역</option>
              <option value={"SEOUL"}>서울</option>
              <option value={"DAEGU"}>대구</option>
              <option value={"INCHEON"}>인천</option>
              <option value={"GWANGJU"}>광주</option>
              <option value={"DAEJEON"}>대전</option>
              <option value={"ULSAN"}>울산</option>
              <option value={"GYEONGGI"}>경기도</option>
              <option value={"GANGWON"}>강원도</option>
              <option value={"CHUNGCHEONG"}>충청도</option>
              <option value={"JEOLLA"}>전라도</option>
              <option value={"GYEONGSANG"}>경상도</option>
              <option value={"JEJU"}>제주도</option>
            </Form.Control>
          </Col>
          <Col>
            <Form.Control
                type="text"
                placeholder="내용 검색"
                onChange={(e) => setDescription(e.target.value)}
            />
          </Col>
          <Col>
            <Button onClick={onSearch}>검색</Button>
          </Col>
        </Row>
      </Form>
  );
};

export default SearchComponent;
