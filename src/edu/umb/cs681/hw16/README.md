I considered a ticket reservation system because if multiple entities try to buy some limited tickets then there are chances of race conditions. 

The code which is: TicketReservationSystem.java is not thread safe. If 2 people try to reserve a seat and only one seat is available then only one person should get it. However, since the code is multithreaded if both threads are reserving seats at the same time, they both will get the ticket but in reality that is not the case.

The way I have fixed it, is that I applied locks on the booking function. This way the number of seats will be updated one time. As in only one person will be able to book the ticket at one time. Therefore, if there is only one ticket left then only one person will get the ticket.
