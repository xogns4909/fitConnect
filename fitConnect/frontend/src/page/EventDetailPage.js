import React from 'react';
import { useParams } from 'react-router-dom';
import EventDetail from '../components/event/EventDetail';
import NavbarComponent from "../global/Navbar";

const EventDetailPage = () => {
  const { eventId } = useParams();

  return (
    <div>
      <NavbarComponent/>
      <EventDetail eventId={eventId} />
    </div>
  );
};

export default EventDetailPage;
