import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useParams, useNavigate } from 'react-router-dom';
import { Form, Button, Card, Row, Col } from 'react-bootstrap';

const EventEditForm = () => {
  const { eventId } = useParams();
  const navigate = useNavigate();
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
        const response = await axios.get(`/api/events/${eventId}/detail`);
        console.log(response.data)
        const { eventDetail, registrationPolicy, location, category } = response.data;

        const formatDateTime = (dateTimeString) => {
          return dateTimeString ? dateTimeString.slice(0, 16) : '';
        };

        setEventData({
          title: eventDetail.title,
          description: eventDetail.description,
          startDate: formatDateTime(eventDetail.startDate),
          endDate: formatDateTime(eventDetail.endDate),
          maxParticipants: registrationPolicy.maxParticipants, // 숫자를 문자열로 변환
          registrationStart: formatDateTime(registrationPolicy.registrationStart),
          registrationEnd: formatDateTime(registrationPolicy.registrationEnd),
          city: location.city,
          address: location.address,
          category: category,
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
      await axios.put(`/api/events/${eventId}`, {
            eventDetail: {
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
      alert('Event updated successfully');
      navigate(`/events/${eventId}`);
    } catch (error) {
      console.error('Error updating event:', error);
      alert('Failed to update the event');
    }
  };

  const handleDelete = async () => {
    try {
      await axios.delete(`/api/events/${eventId}`);
      alert('Event deleted successfully');
      navigate('/events');
    } catch (error) {
      console.error('Error deleting event:', error);
      alert('Failed to delete the event');
    }
  };

  console.log(eventData)

  return (
      <Card className="my-4 shadow">
        <Card.Body>
          <Form onSubmit={handleSubmit}>
            <Row className="mb-3">
              <Form.Group as={Col} controlId="formEventTitle">
                <Form.Label>Title</Form.Label>
                <Form.Control
                    type="text"
                    placeholder="Enter event title"
                    name="title"
                    value={eventData.title || ''}
                    onChange={handleChange}
                    required
                />
              </Form.Group>
            </Row>

            <Row className="mb-3">
              <Form.Group as={Col} controlId="formDescription">
                <Form.Label>Description</Form.Label>
                <Form.Control
                    as="textarea"
                    rows={3}
                    placeholder="Enter event description"
                    name="description"
                    value={eventData.description || ''}
                    onChange={handleChange}
                    required
                />
              </Form.Group>
            </Row>

            <Row className="mb-3">
              <Form.Group as={Col} controlId="formStartDate">
                <Form.Label>Start Date</Form.Label>
                <Form.Control
                    type="datetime-local"
                    name="startDate"
                    value={eventData.startDate || ''}
                    onChange={handleChange}
                    required
                />
              </Form.Group>

              <Form.Group as={Col} controlId="formEndDate">
                <Form.Label>End Date</Form.Label>
                <Form.Control
                    type="datetime-local"
                    name="endDate"
                    value={eventData.endDate || ''}
                    onChange={handleChange}
                    required
                />
              </Form.Group>
            </Row>

            <Row className="mb-3">
              <Form.Group as={Col} controlId="formMaxParticipants">
                <Form.Label>Max Participants</Form.Label>
                <Form.Control
                    type="number"
                    name="maxParticipants"
                    value={eventData.maxParticipants || ''}
                    onChange={handleChange}
                    required
                />
              </Form.Group>

              <Form.Group as={Col} controlId="formRegistrationStart">
                <Form.Label>Registration Start</Form.Label>
                <Form.Control
                    type="datetime-local"
                    name="registrationStart"
                    value={eventData.registrationStart || ''}
                    onChange={handleChange}
                    required
                />
              </Form.Group>

              <Form.Group as={Col} controlId="formRegistrationEnd">
                <Form.Label>Registration End</Form.Label>
                <Form.Control
                    type="datetime-local"
                    name="registrationEnd"
                    value={eventData.registrationEnd || ''}
                    onChange={handleChange}
                    required
                />
              </Form.Group>
            </Row>

            <Row className="mb-3">
              <Form.Group as={Col} controlId="formCity">
                <Form.Label>City</Form.Label>
                <Form.Control
                    as="select"
                    name="city"
                    value={eventData.city || ''}
                    onChange={handleChange}
                    required
                >
                  {/* City options */}
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
                  {/* Add more cities as needed */}
                </Form.Control>
              </Form.Group>

              <Form.Group as={Col} controlId="formAddress">
                <Form.Label>Address</Form.Label>
                <Form.Control
                    type="text"
                    placeholder="Enter address"
                    name="address"
                    value={eventData.address || ''}
                    onChange={handleChange}
                    required
                />
              </Form.Group>
            </Row>

            <Row className="mb-3">
              <Form.Group as={Col} controlId="formCategory">
                <Form.Label>Category</Form.Label>
                <Form.Control
                    as="select"
                    name="category"
                    value={eventData.category || ''}
                    onChange={handleChange}
                    required
                >
                  {/* Category options */}
                  <option>운동 종목</option>
                  <option value={"SOCCER"}>축구</option>
                  <option value={"BASKETBALL"}>농구</option>
                  <option value={"BASEBALL"}>야구</option>
                  <option value={"GOLF"}>골프</option>
                  <option value={"FITNESS"}>헬스</option>
                  <option value={"BOWLING"}>볼링</option>
                  <option value={"BILLIARDS"}>당구</option>
                </Form.Control>
              </Form.Group>
            </Row>

            <Button variant="primary" type="submit">
              Update Event
            </Button>
            <Button variant="danger" onClick={handleDelete} style={{ marginLeft: '10px' }}>
              Delete Event
            </Button>
          </Form>
        </Card.Body>
      </Card>
  );
};

export default EventEditForm;
