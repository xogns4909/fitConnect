import React, { useState, useEffect } from 'react';
import axiosInstance from '../../global/axiosConfig';
import { useParams, useNavigate } from 'react-router-dom';
import { Form, Button, Card, Row, Col } from 'react-bootstrap';
import ImageUploadModal from './EventImageUpdateModal';
import Navbar from '../../global/Navbar'
const EventEditForm = () => {
  const { eventId } = useParams();
  const navigate = useNavigate();
  const [showModal, setShowModal] = useState(false);

  const [eventData, setEventData] = useState({
    title: '',
    description: '',
    startDate: '',
    endDate: '',
    maxParticipants: '',
    registrationStart: '',
    registrationEnd: '',
    city: '',
    address: '',
    category: '',
  });

  useEffect(() => {
    const fetchEventDetails = async () => {
      try {
        const response = await axiosInstance.get(`/api/events/${eventId}/detail`);
        const formatDateTime = (dateTimeString) => dateTimeString ? dateTimeString.slice(0, 16) : '';
        setEventData({
          ...response.data,
          startDate: formatDateTime(response.data.startTime),
          endDate: formatDateTime(response.data.endTime),
          registrationStart: formatDateTime(response.data.registrationStart),
          registrationEnd: formatDateTime(response.data.registrationEnd),
        });
      } catch (error) {
        console.error('Error fetching event details:', error);
      }
    };
    fetchEventDetails();
  }, [eventId]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setEventData({ ...eventData, [name]: value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await axiosInstance.patch(`/api/events/${eventId}`, {            eventDetail: {
          title: eventData.title,
          description: eventData.description,
          startDate: `${eventData.startDate}:00`,
          endDate: `${eventData.endDate}:00`,
        },
        recruitmentPolicy: {
          maxParticipants: eventData.maxParticipants,
          registrationStart: `${eventData.registrationStart}:00`,
          registrationEnd: `${eventData.registrationEnd}:00`,
        },
        location: {
          city: eventData.city,
          address: eventData.address,
        },
        category: eventData.category,
      });
      alert('수정 성공');
      navigate(`/events/${eventId}`);
    } catch (error) {
      console.error('Error updating event:', error);
      alert('수정에 실패하셨습니다.');
    }
  };


  const handleShowModal = () => setShowModal(true);
  const handleCloseModal = () => setShowModal(false);

  return (
      <>
        <Navbar />
        <Card className="my-4 shadow">
        <Card.Body>
          <Form onSubmit={handleSubmit}>
            <Form.Group as={Row} className="mb-3" controlId="formEventTitle">
              <Form.Label column sm={3}>이벤트 제목</Form.Label>
              <Col sm={9}>
                <Form.Control
                    type="text"
                    placeholder="이벤트 제목을 입력하세요"
                    name="title"
                    value={eventData.title || ''}
                    onChange={handleChange}
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
                    name="description"
                    value={eventData.description || ''}
                    onChange={handleChange}
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
                      name="startDate"
                      value={eventData.startDate || ''}
                      onChange={handleChange}
                      required
                  />
                </Form.Group>
              </Col>
              <Col sm={6}>
                <Form.Group controlId="formEndDate">
                  <Form.Label>종료 날짜 및 시간</Form.Label>
                  <Form.Control
                      type="datetime-local"
                      name="endDate"
                      value={eventData.endDate || ''}
                      onChange={handleChange}
                      required
                  />
                </Form.Group>
              </Col>
            </Row>

            {/* Max Participants & Category */}
            <Row className="mb-3">
              <Col xs={6}>
                <Form.Group controlId="formMaxParticipants">
                  <Form.Label>최대 참가자 수</Form.Label>
                  <Form.Control
                      type="number"
                      placeholder="최대 참가자 수"
                      name="maxParticipants"
                      value={eventData.maxParticipants || ''}
                      onChange={handleChange}
                      required
                  />
                </Form.Group>
              </Col>
              <Col xs={6}>
                <Form.Group controlId="formCategory">
                  <Form.Label>카테고리</Form.Label>
                  <Form.Control
                      as="select"
                      name="category"
                      value={eventData.category || ''}
                      onChange={handleChange}
                      required
                  >
                    <option value="">카테고리를 선택하세요</option>
                    <option value={"SOCCER"}>축구</option>
                    <option value={"BASKETBALL"}>농구</option>
                    <option value={"BASEBALL"}>야구</option>
                    <option value={"GOLF"}>골프</option>
                    <option value={"FITNESS"}>헬스</option>
                    <option value={"BOWLING"}>볼링</option>
                    <option value={"BILLIARDS"}>당구</option>
                  </Form.Control>
                </Form.Group>
              </Col>
            </Row>

            {/* City & Address */}
            <Row className="mb-3">
              <Col xs={6}>
                <Form.Group controlId="formCity">
                  <Form.Label>도시</Form.Label>
                  <Form.Control
                      as="select"
                      name="city"
                      value={eventData.city || ''}
                      onChange={handleChange}
                      required
                  >
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
              <Col xs={6}>
                <Form.Group controlId="formAddress">
                  <Form.Label>주소</Form.Label>
                  <Form.Control
                      type="text"
                      placeholder="상세 주소를 입력하세요"
                      name="address"
                      value={eventData.address || ''}
                      onChange={handleChange}
                      required
                  />
                </Form.Group>
              </Col>
            </Row>

            <Button variant="primary" type="submit" className="mt-2">이벤트 업데이트</Button>
            <Button variant="info"  onClick={handleShowModal} style={{ marginLeft: '10px' }} >이미지 변경</Button>
          </Form>
          <ImageUploadModal show={showModal} handleClose={handleCloseModal} />
        </Card.Body>
      </Card>
        </>
  );
};

export default EventEditForm;
