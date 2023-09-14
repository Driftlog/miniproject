export interface Event {

  eventID: string,
  startDate: Date,
  endDate: Date,
  comments: string,
  location: string,
  departureTime: Date
  sendMail ?: boolean


}
