package com.example.servingwebcontent.api;

import com.example.servingwebcontent.models.Booking;
import com.example.servingwebcontent.models.User;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException, ParseException, java.text.ParseException {
        // Testing CovidTest DELETE
        // String api = System.getenv("API_KEY");
        // String covidTestId = "21818dc2-2384-4063-bc8c-ae047d92d2d9";
        // APIfactory testFactory = new CovidTestFactory(api);
        // Delete covidTestDelete = testFactory.createDelete();
        // covidTestDelete.deleteApi(covidTestId);

        // Testing Booking DELETE
        String api = System.getenv("API_KEY");
        // String covidTestId = "90d5b22e-ce89-42e1-aa01-6b2b09586eaa";
        // APIfactory testFactory = new BookingFactory(api);
        // Delete bookingDelete = testFactory.createDelete();
        // bookingDelete.deleteApi(covidTestId);

        // TODAY
//        APIfactory factory = new BookingFactory(api);
//        Get userGet = factory.createGet();
//        Collection<Booking> bookings = userGet.getApi();
//        for (Booking booking : bookings) {
//            System.out.println(booking.getRecentUpdateTime());
//        }

//        String hey = "2014-01-02T11:42:13.510";


//        String hello = "2022-05-20T12:49:30.304Z";
//        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
//
//        Date recentTime = formatter.parse(hello);
//        System.out.println(recentTime);
//
//        String currTime = LocalDateTime.now().toString();
//        Date currentTime = formatter.parse(currTime);
//        System.out.println(currentTime);
//
//        System.out.println((currentTime.getTime()-recentTime.getTime())/1000);
        APIfactory bookingFac = new BookingFactory(api);
        Get bookingGet = bookingFac.createGet();
        List<Booking> bookings = (List<Booking>) bookingGet.getApi();
        System.out.println(bookings);
        System.out.println(checkDifferenceTime(bookings));



    }
    public static List<Booking> checkDifferenceTime(List<Booking> bookings) throws java.text.ParseException {
        // check the time difference of time and local time
        // see whether it is smaller than the set range in second
        int timeRangeAllowed = 600;   //In second
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        String currTime = LocalDateTime.now().toString();
        Date currentTime = formatter.parse(currTime);

        List<Booking> ret = new ArrayList<>();
        for (Booking booking : bookings){
            System.out.println(booking.getRecentUpdateTime());
//            if(!booking.getRecentUpdateTime().equals("")){
//                Date recentTime = formatter.parse(booking.getRecentUpdateTime());
//                if((currentTime.getTime()-recentTime.getTime())/1000 < timeRangeAllowed){
//                    System.out.println((currentTime.getTime()-recentTime.getTime())/1000);
//                    ret.add(booking);
//                }
//            }
        }
        return ret;
    }
}
