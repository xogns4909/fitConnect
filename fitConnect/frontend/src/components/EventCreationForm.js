import React, { useState } from 'react';
import { Form, Button, Row, Col, Card } from 'react-bootstrap';

const EventCreationForm = ({ onCreate }) => {
  const [title, setEventTitle] = useState('');
  const [description, setDescription] = useState('');
  const [startDate, setStartDate] = useState('');
  const [endDate, setEndDate] = useState('');
  const [maxParticipants, setMaxParticipants] = useState('');
  const [registrationStart, setRegistrationStart] = useState('');
  const [registrationEnd, setRegistrationEnd] = useState('');
  const [city, setCity] = useState('');
  const [address, setAddress] = useState('');
  const [category, setCategory] = useState('');

  const validateForm = () => {
    if (!title.trim()) return "제목을 입력해주세요.";
    if (!description.trim()) return "설명을 입력해주세요.";
    if (new Date(startDate) >= new Date(endDate)) return "종료 시간은 시작 시간보다 늦어야 합니다.";
    if (new Date(registrationStart) >= new Date(registrationEnd)) return "등록 종료 시간은 등록 시작 시간보다 늦어야 합니다.";
    if (maxParticipants <= 0) return "인원은 1명 이상이어야 합니다.";
    if (!category.trim()) return "카테고리를 선택해주세요.";
    if (!city.trim()) return "도시를 선택해주세요.";
    if (!address.trim()) return "주소를 입력해주세요.";
    return null;
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    const validationError = validateForm();
    if (validationError) {
      alert(validationError);
      return;
    }
    onCreate({
      eventDetail: {
        title: title,
        description: description,
        startDate: startDate,
        endDate: endDate,
      },
      recruitmentPolicy: {
        maxParticipants: maxParticipants,
        registrationStart: registrationStart,
        registrationEnd: registrationEnd,
      },
      location: {
        city: city,
        address: address,
      },
      category: category,
    });
  };

  return (
      <Card className="my-4 shadow">
        <Card.Body>
          <Form onSubmit={handleSubmit}>
            <h4 className="mb-3">이벤트 생성</h4>

            <Form.Group as={Row} className="mb-3" controlId="formEventTitle">
              <Form.Label column sm={3}>이벤트 제목</Form.Label>
              <Col sm={9}>
                <Form.Control
                    type="text"
                    placeholder="이벤트 제목을 입력하세요"
                    value={title}
                    onChange={(e) => setEventTitle(e.target.value)}
                    required
                />
              </Col>
            </Form.Group>

            <Form.Group as={Row} className="mb-3" controlId="formDescription">
              <Form.Label column sm={3}>설명</Form.Label>
              <Col sm={9}>
                <Form.Control
                    as="textarea"
                    rows={3}
                    placeholder="이벤트 설명을 입력하세요"
                    value={description}
                    onChange={(e) => setDescription(e.target.value)}
                    required
                />
              </Col>
            </Form.Group>

            <Row className="mb-3">
              <Col sm={6}>
                <Form.Group controlId="formStartDate">
                  <Form.Label>시작 날짜 및 시간</Form.Label>
                  <Form.Control
                      type="datetime-local"
                      value={startDate}
                      onChange={(e) => setStartDate(e.target.value)}
                      required
                  />
                </Form.Group>
              </Col>
              <Col sm={6}>
                <Form.Group controlId="formEndDate">
                  <Form.Label>종료 날짜 및 시간</Form.Label>
                  <Form.Control
                      type="datetime-local"
                      value={endDate}
                      onChange={(e) => setEndDate(e.target.value)}
                      required
                  />
                </Form.Group>
              </Col>
            </Row>

            <Row className="mb-3">
              <Col xs={4}>
                <Form.Group controlId="formMaxParticipants">
                  <Form.Label>최대 참가자 수</Form.Label>
                  <Form.Control
                      type="number"
                      placeholder="최대 참가자 수"
                      value={maxParticipants}
                      onChange={(e) => setMaxParticipants(e.target.value)}
                      required
                  />
                </Form.Group>
              </Col>
              <Col xs={4}>
                <Form.Group controlId="formRegistrationStart">
                  <Form.Label>등록 시작 시간</Form.Label>
                  <Form.Control
                      type="datetime-local"
                      value={registrationStart}
                      onChange={(e) => setRegistrationStart(e.target.value)}
                      required
                  />
                </Form.Group>
              </Col>
              <Col xs={4}>
                <Form.Group controlId="formRegistrationEnd">
                  <Form.Label>등록 종료 시간</Form.Label>
                  <Form.Control
                      type="datetime-local"
                      value={registrationEnd}
                      onChange={(e) => setRegistrationEnd(e.target.value)}
                      required
                  />
                </Form.Group>
              </Col>
              <Col xs={6}>
                <Form.Group controlId="formCategory">
                  <Form.Label>카테고리</Form.Label>
                  <Form.Control as="select" value={category} onChange={(e) => setCategory(e.target.value)} required>
                    <option value="">카테고리를 선택하세요</option>
                    <option>운동 종목</option>
                    <option value={"SOCCER"}>축구</option>
                    <option value={"BASKETBALL"}>농구</option>
                    <option value={"BASEBALL"}>야구</option>
                    <option value={"GOLF"}>골프</option>
                    <option value={"FITNESS"}>헬스</option>
                    <option value={"BOWLING"}>볼링</option>
                    <option value={"BILLIARDS"}>당구</option>
                    // 추가 카테고리 옵션
                  </Form.Control>
                </Form.Group>
              </Col>
            </Row>

            <Row className="mb-3">
              <Col>
                <Form.Group controlId="formCity">
                  <Form.Label>도시</Form.Label>
                  <Form.Control as="select" value={city} onChange={(e) => setCity(e.target.value)} required>
                    <option value="">도시를 선택하세요</option>
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
                </Form.Group>
              </Col>
              <Col>
                <Form.Group controlId="formAddress">
                  <Form.Label>주소</Form.Label>
                  <Form.Control
                      type="text"
                      placeholder="상세 주소를 입력하세요"
                      value={address}
                      onChange={(e) => setAddress(e.target.value)}
                      required
                  />
                </Form.Group>
              </Col>
            </Row>

            <Button variant="primary" type="submit" className="mt-2">이벤트 생성</Button>
          </Form>
        </Card.Body>
      </Card>
  );
};

export default EventCreationForm;
