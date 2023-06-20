package com.tugasakhir.configuration;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class EmailOtpService {
    private final JavaMailSender javaMailSender;

    public EmailOtpService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendMail(String toEmail, String subject, String token, String username) throws MessagingException {

        String from = "mimsaplication@iconplus.com";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "${spring.mail.host}");
        props.put("mail.smtp.port", "2525");
        props.put("mail.smtp.ssl.trust", "*");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("${spring.mail.username}", "${spring.mail.password}");
                    }
                }
        );
        MimeMessage mail = new MimeMessage(session);
        mail.setFrom(from);
        mail.setRecipients(Message.RecipientType.TO, toEmail);
        mail.setSubject(subject);

        String message ="<!DOCTYPE html>\n" +
                "<html lang=\"en\" xmlns:o=\"urn:schemas-microsoft-com:office:office\" xmlns:v=\"urn:schemas-microsoft-com:vml\">\n" +
                "    <head>\n" +
                "        <title></title>\n" +
                "        <meta content=\"text/html; charset=utf-8\" http-equiv=\"Content-Type\"/>\n" +
                "        <meta content=\"width=device-width,initial-scale=1\" name=\"viewport\"/>\n" +
                "        <!--[if mso]><xml><o:OfficeDocumentSettings><o:PixelsPerInch>96</o:PixelsPerInch><o:AllowPNG/></o:OfficeDocumentSettings></xml><![endif]--><!--[if !mso]><!-->\n" +
                "        <link href=\"https://fonts.googleapis.com/css?family=Cormorant+Garamond\" rel=\"stylesheet\" type=\"text/css\"/>\n" +
                "        <link href=\"https://fonts.googleapis.com/css?family=Open+Sans\" rel=\"stylesheet\" type=\"text/css\"/>\n" +
                "        <link href=\"https://fonts.googleapis.com/css?family=Lato\" rel=\"stylesheet\" type=\"text/css\"/>\n" +
                "        <link href=\"https://fonts.googleapis.com/css?family=Droid+Serif\" rel=\"stylesheet\" type=\"text/css\"/>\n" +
                "        <link href=\"https://fonts.googleapis.com/css?family=Fira+Sans\" rel=\"stylesheet\" type=\"text/css\"/>\n" +
                "        <link href=\"https://fonts.googleapis.com/css?family=Lora\" rel=\"stylesheet\" type=\"text/css\"/>\n" +
                "        <link href=\"https://fonts.googleapis.com/css?family=Quattrocento\" rel=\"stylesheet\" type=\"text/css\"/>\n" +
                "        <link href=\"https://fonts.googleapis.com/css?family=Permanent+Marker\" rel=\"stylesheet\" type=\"text/css\"/>\n" +
                "        <link href=\"https://fonts.googleapis.com/css?family=Oswald\" rel=\"stylesheet\" type=\"text/css\"/>\n" +
                "        <link href=\"https://fonts.googleapis.com/css?family=Montserrat\" rel=\"stylesheet\" type=\"text/css\"/>\n" +
                "        <link href=\"https://fonts.googleapis.com/css?family=Merriweather\" rel=\"stylesheet\" type=\"text/css\"/>\n" +
                "        <!--<![endif]-->\n" +
                "        <style>\n" +
                "            *{box-sizing:border-box}body{margin:0;padding:0}a[x-apple-data-detectors]{color:inherit!important;text-decoration:inherit!important}#MessageViewBody a{color:inherit;text-decoration:none}p{line-height:inherit}.desktop_hide,.desktop_hide table{mso-hide:all;display:none;max-height:0;overflow:hidden}.menu_block.desktop_hide .menu-links span{mso-hide:all}@media (max-width:620px){.desktop_hide table.icons-inner,.social_block.desktop_hide .social-table{display:inline-block!important}.icons-inner{text-align:center}.icons-inner td{margin:0 auto}.fullMobileWidth,.image_block img.big,.row-content{width:100%!important}.mobile_hide{display:none}.stack .column{width:100%;display:block}.mobile_hide{min-height:0;max-height:0;max-width:0;overflow:hidden;font-size:0}.desktop_hide,.desktop_hide table{display:table!important;max-height:none!important}}\n" +
                "        </style>\n" +
                "    </head>\n" +
                "    <body style=\"background-color:#fff;margin:0;padding:0;-webkit-text-size-adjust:none;text-size-adjust:none\">            \n" +
                "        <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"row row-2\" role=\"presentation\" style=\"mso-table-lspace:0;mso-table-rspace:0;background-color:#f7f6f5\" width=\"100%\">\n" +
                "            <tbody>\n" +
                "                <tr>\n" +
                "                    <td>\n" +
                "                        <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"row-content stack\" role=\"presentation\" style=\"mso-table-lspace:0;mso-table-rspace:0;background-color:#eee;color:#000;width:600px\" width=\"600\">\n" +
                "                            <tbody>\n" +
                "                                <tr>\n" +
                "                                    <td class=\"column column-1\" style=\"mso-table-lspace:0;mso-table-rspace:0;font-weight:400;text-align:left;vertical-align:top;padding-top:0;padding-bottom:0;border-top:0;border-right:0;border-bottom:0;border-left:0\" width=\"100%\">\n" +
                "                                        <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"image_block block-2\" role=\"presentation\" style=\"mso-table-lspace:0;mso-table-rspace:0\" width=\"100%\">\n" +
                "                                            <tr>\n" +
                "                                                <td class=\"pad\" style=\"width:100%;padding-right:0;padding-left:0;padding-top:10px;padding-bottom:10px\">\n" +
                "                                                    <div align=\"center\" class=\"alignment\" style=\"line-height:10px\">                                                                                                                                                                                                               \n" +
                "                                                        <div style=\"display:block;height:auto;border:0;width:320px;max-width:100%\"  >\n" +
                "                                                        <svg version=\"1.1\" id=\"Layer_1\" xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink=\"http://www.w3.org/1999/xlink\" x=\"0px\" y=\"0px\"  viewBox=\"0 0 458 74\" enable-background=\"new 0 0 458 74\" xml:space=\"preserve\">  <image id=\"image0\" width=\"458\" height=\"74\" x=\"0\" y=\"0\"\n" +
                "                                                         href=\"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAcoAAABKCAYAAAA7WxSwAAAABGdBTUEAALGPC/xhBQAAACBjSFJN\n" +
                "                                                        AAB6JgAAgIQAAPoAAACA6AAAdTAAAOpgAAA6mAAAF3CculE8AAAABmJLR0QA/wD/AP+gvaeTAAAA\n" +
                "                                                        CXBIWXMAAA7DAAAOwwHHb6hkAAAokElEQVR42u2deXxURbbHf3W7O71k6e6w72FTUISwOO7SwQV9\n" +
                "                                                        ggSfbCImiAPjzLzXQZ7L6GNCHH2jM2KSGbcZdJIoW1AnYQZUdDQNjLiwJAKyQ8ISAoGku7P2Wuf9\n" +
                "                                                        cW+HTtIJCQk0SH0/n/5A7q06dU7dpH/3VNWtyyBoM5XTphn9jF2j7hUTL50+3ZdpDPWI0u1TG7oW\n" +
                "                                                        GpYuPRpu/wQCgUDQ+bBwO3Al4Hx89oN04tg8/+lTP2Oc96SqKkCtAXxeMI0GLDLaxSP1ezVDr30n\n" +
                "                                                        Jif3r+H2VyAQCASdhxDKVqicPuWXrOSI1V9edg0jgKk1gMEA8nnAfBzQ6QAiwOcDvB7w2jqwAQOK\n" +
                "                                                        9GNvTDS89TeRYQoEAsFPACGUIahPWzzR9c+P0/nxo8PBOZg5FoiMAmOqau5xuZhaLUGtjkFNjYbq\n" +
                "                                                        asH0BkCrBRgDlZcDMcYK/uqfBnZLTKwOdywCgUAg6BhCKIOg1NQIe8Enb/HDB+aR2wXV4CEn0KX7\n" +
                "                                                        15I+6u9k7rHV1KVLZcnqV91xE+dLtSOHxPi/3jSQSPOw/8C+qazy7EDoDYBWBzp9Eix+3OrYTwtm\n" +
                "                                                        hTsmgUAgEAg6herfLBpRObCX84xRIvuY63+sSZ79BKWmSm2pSwCzT56YVjG0N1UM7UOVIwZRRW8T\n" +
                "                                                        uf748nXhjksgEAgEgg5T/ezCR8+aNVRxfZy35vFH5lyoHeev5z1a0TeWKq8bSGe76Mm5IOmtcMcm\n" +
                "                                                        EAgEAkGHqJw1/fUKo4rsd9++mtakRnTUXo31V8+ejdVRxbV9yT5qyFlaM00V7hgFAoFAILggKocP\n" +
                "                                                        3FCuAjlTn36wU+3ePnJLRbcoso8YTFX/ax0e7jgFAoFAcOG0aQ7up4j9tnFboMGtkWkv9TWm/fEf\n" +
                "                                                        nWlbc9cD86HTgTsc4A7nyHDHKhAIBAJBu7CPGb6zLEqz/2K24XggYeeZaImqH7zv5XDHKxAIBIIL\n" +
                "                                                        56rLKM/G6reotFp3rxrvtRezHTZwaB5cBD+oT7hjFggEAsGFow63A5eSqgcSsrhaWx6z9rPEi96Y\n" +
                "                                                        KWqrpAEQGdk93HELBAKB4MK5aoSy8sF7X/WeKuva5ftdky9Fex63aqOqRzdQ5VlzuGMXCAQCQSez\n" +
                "                                                        6IOtSR9/e7xvuP3oLGr+e8G8qjvGfXqp23XED6urnHDzvnDHLxAIBIILJ+Qc5b6yKkP6ut0vhdu5\n" +
                "                                                        zqDm3TcfqP/8s/uiHp6TeKnbJkNEuVofGe4uEAgEAkGnQ8SkGR+cmZ+1/a5wu9IRXKtyrnFcPySL\n" +
                "                                                        Cv7RNRztOybfu7P82n7fhLsfBAKB4KcEr8IhXoWKS/Q51KIjd734Vbph1kpHuDvkQqFt/zCcmZjw\n" +
                "                                                        Z/cHfxsTLh8qe5l3VCbP+Hu4+0IgEAh+SvAqVFEV6FJ8eBWqWnw8ZNywmCV1J5zGh9/4Oi3cnXIh\n" +
                "                                                        1Ly++gn99SPXaec8viNcPhC4V8N0Yo5SIBAIOhdvu0pHANArH23722pRKF+dPs7Z94behR9lb/tt\n" +
                "                                                        au7u2HD3SnuwP/nzBGbWV0e9nrkhnH6w/nF2MmhKwt0fAoFAcNWiBvyHIuD9twHer/XwFerabaLV\n" +
                "                                                        DQcm/6z/c+CEj7Yeyw93rG2F1uT0Z/766yLfePf9cPuiGjLYJUnSsXD7IRAIBFclEgA/Q81/90TV\n" +
                "                                                        1H5w/kd/uN6OBQztN9MibyeP/twwqk/1j7ZDd8zP2f5QuGM+H1RQoK7buv0/tNNmfsoY84fbH395\n" +
                "                                                        uR+xXU+F2w+BQCC4KtEBvu/08BXpwPR+SDEcusftALXPzHm3sBs7MDYdahVy1u15N3fLMX24426N\n" +
                "                                                        2vyP70H3LoX6eyYdCbcv1amLuquM5jJDTJeLuqesQCAQCFpABXi36wE/QB4J6p+5oL65HnC1z8x5\n" +
                "                                                        hXLCsB7voY8R7lN15pf/vnNZuONuCdcrLw73mLpoIp9O/S7cvgCA/3DpKGaIOi4tWlQfbl8EAoHg\n" +
                "                                                        qoMBqAe8XxkANQFeQPtQlbywp7MzyrTpI44N6W/8F3Rq7Cwsmz1/2Tczwh1/UygrS1d/YO91Zovl\n" +
                "                                                        q3D7EoBx73ApUh/24V+BQCC4apAgb8yqgbzCtUQF3wEtCAwxIx3QTnECHsgi2rS8Kuh4E9q01+vN\n" +
                "                                                        g7q+d2h/xd1w+vDPHWXPAsgNd38EU1N+7Bb9+Al7WEJCTbh9CUCe+miSTMXh9kMgEAiuBggSjtQO\n" +
                "                                                        xteV43Gorj+u730Qd67dAn0FQadyYf2NE1BYMQK32b/DOOO3iNWfwX73cHx6chIq3dG41bQNt5tt\n" +
                "                                                        iFJXNcs42ySU80a4Plq+UbMKNW4snjby5798O9xdcg57qjWOVzv0umdT94bbl2CMOnaGNFJBuP0Q\n" +
                "                                                        CASCnzwqYLczHiO3fAN4IuTMsATI++JRTIr4EuURXfDE4KWoKOwNMCDaXI5xmt2I2ubBOsMEkDkC\n" +
                "                                                        8AK/HroMfx4xH2gyFtgmoUxISPANS/nHh2PvHnLolxOGbA93nwSgNWtUdYf3jjJcP/prID3c7jSw\n" +
                "                                                        bt1m82c7d526//lfloXbF4FAILga8PAITO72BR7qsRb9Y4pR+OM4jD2yCx5vBEof6IVXHn4Jg3X7\n" +
                "                                                        8PUOC4rXxuE/N32CoaoSPPTex+hnLsWG0/cggqnaPX952eN4JfVn/P8W3xhuP5qS+N7O6aNf3/6L\n" +
                "                                                        cPshEAgEP0V4FSqabjXndWqIakBUDyICUT6owjSUzvYZSv5vtUR7QPR0JPlGdSOPuRfV6vuT580Y\n" +
                "                                                        uaxLrudz6kJtYVdxxb6Pkn/yXjf84/Mu0ju5l/z1Wefj7LFT9/o8rlXh9kMgEAiuFtSSF5AAe313\n" +
                "                                                        lLv6osvqKkgcUHX3ofb1rvB+q0fEGR9g4HDrdND3rIVmchXKnANRVx+DOMNeqDQuwNfc9nlXvV62\n" +
                "                                                        HD5oPjT81stmlWuAlz8uGuH2cMPi0ZNt4fZFIPipQEZjPBmNpjaWNZHRGH/JfDuCaeHqF4GCBJS5\n" +
                "                                                        +2J24fsY+PU+3LJuA5w7zNBEecFPqyB9ooLKyfHd8DH4fviNMNRW43D8YDxuz8Ggr4owZPMOjPj3\n" +
                "                                                        Tqw+8VhIVQyZUa7YfNT8vyt2pBaftPdikVo3ABBxAABjEsjrV6lVEn/YMvL91U+O/KI1/+94/tNe\n" +
                "                                                        JqNu7K6jjptVKjYsNlqvr673MEmtgsfrNzlqPd1jDBq1hkPiEv/8YGbiEy3ZGvfCZ/2M+oiR9c66\n" +
                "                                                        m8bvPnNLad9hLtN/fRqnqa+JVWvUddE61eHe5kjP0bIqV3m1y3FvfJ8Sr9+/56zdvfvbV+47hPMw\n" +
                "                                                        +6/f9v2usOwltUbdh4POeLh/48Tre274y5O3lLT1en256/TTBHZs+vRLvDPQjFVxIF6ANbMHNj6+\n" +
                "                                                        YgkAI3JnL2x0fPqKYjApAbmz2hxbI5uE8QAAxkwgFMHtXoi1cx2XLN7pKwqwZnZCW4qS2Rxy1oEA\n" +
                "                                                        G+N8IXM6iy6Z3xcAN5sLGGAB5wnM6bSFjMVojCNJSgWQyAATAQ4A+YzzNOZ0loQ7ho5ARmM8JKmQ\n" +
                "                                                        gBIAA89bXpKyGJBIRuNc5nRmX3T/gAQAH4a7n65qGHC4bghWHp+DG/ttxUOF69Hz9Cm4JR08pEHR\n" +
                "                                                        daNwbHIfbJhowbTX/wn6gTB32FJsPzwOIyO3oX/kcWw5Y8Gak7Mws0/z3U+bCeWaNWtUC7K+32ov\n" +
                "                                                        rRrMYiMBToofiswSwDQq+Ko9KNi+hwCEFMrZb2+6btehut/8e/+ZmeSHGgyA14/DnrOAWgXUuoHI\n" +
                "                                                        CHTvazx6pNRZCXu9d+rEIYUHQ9iav+z7h77acfyJHfsr7ud+P+AlQB0J7NsJmLXeoQNivzxaXj2k\n" +
                "                                                        rNI98YD3NBAZARgikPf5AcAQAXCO/gvyN/85+eZpU27peTqUv1lfFw9b8Md/7/DUevRQM0CSAK1q\n" +
                "                                                        1nvlxYhb8NHfr+/fNWP9C5bNrV2rFdtKu/76rW/uSrYMtGxroUzG54eGvpO3c3H3LlGxfpXSp9Vu\n" +
                "                                                        xPSIOfLMz25/KiGB+XAh5M4qwYyVDkxbHo8PHy0K+g2aAiJHo7LTlseDMccFiySARiI1Y5UFuogU\n" +
                "                                                        AEsuyPcwwQALSVI65C+6KxYyGuNJkgoYYAqKzQQgmSQpkYzGhMv9ZiCYEDcGDkX42xpDCQEOJgsr\n" +
                "                                                        yGxeAiAVQBqz25d0usOE68PdZ1c9HBikP4yNt9yNO/t9Cf6mCXapG3STanDi/v7A7bWY1i8LyY4/\n" +
                "                                                        oX53b5y9rhsmJa7FQv2fMLPnGqg0bpyp7Y1DtcMA3tx8M6HMOWxOsh8sHoy+MQBRyAcwyccBEObf\n" +
                "                                                        OzTzd+82P3/vS18+vnJD8XtU7wNi5F0RGGMgrRpgOqDOg8heurV3xA/49WfPJZwI1Mv7srGdZ9ds\n" +
                "                                                        My7LO7T6rx//eB9UTBZAlRpML4Gq6mHoY/yibuXMewPi2m1e3t4zZ6qHwaCR3Y6NBBEBYDi259Qd\n" +
                "                                                        i1Z/9zaAZnvWfvDt2Zg5v//HFrihZ93O3RwQAJ+Xo6S07qGSsyceGrlw7QqjPuLpzf93f8jVrC9k\n" +
                "                                                        bV/FJFae/tjYFrPXd7/a//6+baU374vWAUzp3BoPWH+jN9G0fVHHflvIBiZZEPhCmZJlAlACxuSM\n" +
                "                                                        MyCMTLIAZGtUdcYqCwAgd5at9SYwvlkmJ9c5Vy8g1lOyTNDp4gGUhBTlwHnudzQW9waf4gDEtVi/\n" +
                "                                                        eXlLa/4zu73htzmQpTDA0ixEedjOBKCIOZ2Olo4pxy0AEJzptVLWBCAegIM5nUWBcqGyRMWuoy0C\n" +
                "                                                        R5KUp2SRDRmykmGmMyCR5BiLgmw39GtwttkW/5rWbc3PltoJcb6hPhmN8aQIPgHxZDSWAHAwzqcC\n" +
                "                                                        cDT1MVRb4DwHwFoAcj8AA5hsb0DQkGybrmWbYOjZrvIKVqs1kTGW3uA3kJ2RkZF2njoWxlhqRkZG\n" +
                "                                                        m27urFZrHGMsPSMjY2prthRfRp2v/c4mJSUlT4k/v0NtE9Bbdxy9jccBByBd50OXpw4CY4GRKJNf\n" +
                "                                                        ysUBHJdQfzwS/RYfwpKRL8mbD/jlT7fIk+hmONm2OcrdZfXPI0oDRq2ska3zwNQz6tTvpo9t9qjI\n" +
                "                                                        M6t+eOTzzw68R5DAYg1gKoApgsAYAEc9YrvoT9euTk4MFsmm5Hyxp8t76w/trqyouw8xWrBoLZjE\n" +
                "                                                        ZMGt9UBj0DhqV8yYGFyHS3QUUmNtZ4zJ7cYaUFHtvT1UW8/l/Hs9ariZxRoaRBKQ7TCNBBYdAUgS\n" +
                "                                                        du6vnL1575n9ia9tnt/Uxpw3vn685MCZuxdOGpHSUky5W47pd+89ezP6mcC6R4F1iwTrFglEajDr\n" +
                "                                                        jgHzFywY1753rDW/nGvBlCFRANBpEkHYCPC1gN8SFNh4QFoLQBa1GSsLAW4BuAUzVhZi+orEFptg\n" +
                "                                                        2IgZy5NbdYOxdMxYngytNh3gFhDPwowVjZ/fmbE8GdqIPIBbILEkTF9RgGnL4xvOT1+ZBaJUuT6l\n" +
                "                                                        YvrKrBbbm5JlwvQVBYA/rjW3yGi0NHwUgVSG8wLn47nZXAxJKoQkFUCS7Eo2ApKkJEhSAQHJQeUT\n" +
                "                                                        IUkFypBnyPrcbA6OO14pn8XN5sJAOW42Fwfm3xQbdqV+ITebCxCUKYaIKZEBcQSUMM6nBsSDOZ0l\n" +
                "                                                        jPO54Hyg5HRmKGVN3GwugCQVK/aLudlcEDT3d17/IEnJSpn0Jn7mBfkUqp284DlGbjbnBZ0vDPih\n" +
                "                                                        iHs8ADBJSockJQf5la74UEBS42eUSZLyIB+LI7legVIvmSnXjMkZdnpbrmU7afd7mxQByyKiqRkZ\n" +
                "                                                        GYyIEgDEWa3WuPbaOg8OItrYhnIlRPRDJ7d9vj6wAIjLyMgY3RkC7eFabCq7C7/c/y4SJnyEZ7Tp\n" +
                "                                                        2HN6BE5X9sLnpQ/g0aJcPPvlq0AksPfOoais64KvT9+BWdtzMeG7Dfjjgd/iZF2/kMlho4zy4cxN\n" +
                "                                                        9320dt9g9Ihu8VkSAgEuL24b2vO99U3OLd1yTL8ofdNymPRgenUzG+TngNeHx++55qnX/tZ60Aty\n" +
                "                                                        dm92nazui57RYEHiBQagog5Tp9+wlDHWqAWVxGKDha6p5xo1szc9OjVzyxN5awpvR79YtFiXCEzF\n" +
                "                                                        gBgdyMuj8zce+UufBWueiB9ofvN4pa/a6/Xd/sGHOxf2uKbr/sWJ124KVEvNKzTlbTo279bhPWNM\n" +
                "                                                        kRGeV/N3WeDhgEkrZ+sAiAgsUosTdvfE3+bu6nOszBFxss595vPn736j3b8pubNsmLEy75zf0ngw\n" +
                "                                                        lgaoIIsOspUzFuTOmipnbJSK3EdGB1lZgukrszBjVVHILC539hLMWLFEFleygVNOyGyQowgfPpLd\n" +
                "                                                        8POMFbJ45j6arQhxXKPMdEqWCTptFgD5zpextEbtT1+Z1XxYWamnjcgDoxzkPpqN1gj6clUyjECm\n" +
                "                                                        AuVLWj7P+VwAJSRJVgakktFYAs4zIUkpkCQrgAy5e6UpDADjPCdQX8nsshlwlIAkBqSQ2ewMHvJj\n" +
                "                                                        QDwB+QDWEmBVhC4ZQEaj7BDYCGB8QDhaiClesZkTnB0BgPJzwzHFtiVgm2TbFpKkPAQNP7fmX1CZ\n" +
                "                                                        RAIy5PEapChzgvHM6SwKtAMgDZzbIEkWBqSSJDkAzOVmcyDTLWFADgEDIJePY5znkCTFKW1mM85t\n" +
                "                                                        TWPiZnM2A5LJaExmTme2kk3GE1AiOZ02bjafEzvObSRJ4xvilrNNW2vXEu2F0LfddZRMGsqNWmZm\n" +
                "                                                        ZgmAuYGTKSkpBYGs0Wq1xjPGkjIyMhrWGSgZoBWywC3MzMx0KOVSoWTLRJQGII4xNqCFemuD/IkP\n" +
                "                                                        KmNSylggC21aZmZmUcAPItrIGJsCwBScqVqt1hQAJZmZmfnBgSqZqxWAiYhyMjMzswEgkE2npKSk\n" +
                "                                                        B8d2QaiAnfZ4jP/mX/Ift8oDm3ci/qiZD526Bi5Xd8AA/G7rS/hsqAWzz/4Vkf86iVpPD8CvAlQ+\n" +
                "                                                        FJy8F7trrkNO/MzWNxw4dLr2GahUYNSKQxxAlBYDuqs/anpq5bo9/4tKF2Pdo2TRaarMbh/0vWOq\n" +
                "                                                        Xps1amVrMT/65vf/s3xN4XDWK6aZeJHbj4jeMdVjevb885qg4w8v3aLP31YyHBEtPPHi8iI2Uruz\n" +
                "                                                        POjQJwequj38m7VvITaypS3+msE0EkitQWmZ68bSspPZiFABPg6AYepNg15+J2jb+A3bTr+9c9vJ\n" +
                "                                                        mTt3nAL8HIjWgsUaAH9wTAyI0WLT1uMzN315EKisR/fbB54A0H6hlLGdG4KkOOQ+UgIAmLEyXv53\n" +
                "                                                        laVh2JUoEYx+aBh2behk+gFEiQj6YmxE7uwlAJZg2vJ4SCwJM1ZmgfO5jUSsqaC5PGmKEGaDsSQQ\n" +
                "                                                        5YRo99wcK/F4zFiZDiITwNaC0VEwlalR+YBIEi3EmhBi3QQKHh4G5C9jZY6SgGRFoPKhfHkxznMg\n" +
                "                                                        SYkkSUmS3Z7NzWYbAyzK8F0JgGQCHJL8ZZ2szAumSYooktGYQZJUDCAJQfO3BBRJdrss0GYzAKQy\n" +
                "                                                        SQqs1IwjwCbZ7Q3C1TBn1wEUMbG0ZFs536p/TfoyQ7LbFyo24hX/AjFYlAxX7m9ZrJKUzG5uoN8Y\n" +
                "                                                        56ObijuAIm42J0ERTeZ02gJDogGU65JMkjQFQLZyQwNwntk0buZ02shstgCwMGBjYHFPa9eyvX3L\n" +
                "                                                        WPufHsjMzLSlpKTkM8bsKSkp+Ypo5WdmZgb6IzhmExrfLMUzxkYRURpjLIkxVgBgNGOsgIgSFFGz\n" +
                "                                                        QBbjhrqKSGYR0VzZb2Y9F8M5MQWQTERHAaQxxsYzxvIgL6IyAUhmjDmIKJMxZrVarYkBYWSMWYko\n" +
                "                                                        +KY7IJJ5RLQQgIMxlpqSkjIgIyMjjYhyFOFt/81JUwiQwJHc/wP8vN8yGDV2fG+/FUuP/grV3mjc\n" +
                "                                                        0W855l+bhes+PoKDSQMx3rQJhyriMCZ2MxYPeg299CfwbeVtOO3uFzJJbFCVJ5d9M/Ltj/YmIPY8\n" +
                "                                                        b9Kq9aBHv5hjb829qajpqf0nnPNg0MgNhVIeD+GmwbG5tlbMp6aS9IdvcxcjUttw29+4fTeuHd1n\n" +
                "                                                        /XPTBzuDD8cYvDN8Fa6oUP4T5MU5fUwR2fuCjv8h99vX6spqNWygGeT3ywuV2PklkzEGGCLk+U8G\n" +
                "                                                        wMURE2cqe3vemOXvKGt2f79up/mF5btnotu57Jzp1Y1FkjHA7wdVe8CitCCDFtBrMOe2Ab9Y+s4F\n" +
                "                                                        /bpAHmZlUzBtuQMsePED2eShTT4FUO4kGZlAbAAYtzQOEAAk23mbksVQmYvUFgAY3WLZtXMdmL7C\n" +
                "                                                        JLtCJjDEN5s1ZzgKpnIoYp4KsKlY80gJZqySh19ZkNAxJotkOwgWCADgZnNhw5elIgQMSIQkJYa8\n" +
                "                                                        7vIXtIUkyco4DwxnZcvGpTjZ6LkMSMl+ikKInKMFFwM+tGWoTAmCF0GSQEASGY0ZIeZETcr8YFwo\n" +
                "                                                        28rPlsD58/gXXM/ZwqlADHFNM/ig/8sZc3ORbBNMzhpLlCw2kO2CnRsxOb+N1q7lJSIjI2Oh1WrN\n" +
                "                                                        BGBRMrR0q9U6WskuW6MoaKjSlpKSUmi1WuMBlDDGsgLCGySYcsyyKM0NCJvVanUoGWhTbIHMVBHE\n" +
                "                                                        uFBtW63WHKVcvtVqTQZgCxL6QJtWJePNVuoUMcYKAaRBnjefkpmZWdThzuTA6JjtyBr1mPwzAdfH\n" +
                "                                                        7MZjvbPh4VrotdWAB6iP6oZbphbAFlGAOk8MDOoqeQKSgBExP8jzkyEW8zTcCe0sq/stfBxMakUo\n" +
                "                                                        GAC3FwO76jc0PTX/3e231FS7e0CnRihJJgKgUUEFrEcreIYX3VJf6YpBVETo4V+VBKNO83nTw1/s\n" +
                "                                                        rpwDjdRcnyUJKK9C1z4x+79Mvbeh7eezt/SxbS17DL1iQLUewOUHvD5QtRvk8aO1KdrAlWBMmXd1\n" +
                "                                                        uTD22h4vBA8FJ48d4bmmT/QsnHLO0USpZl47IPp9uJvk836OmKgI9OxrfIqqvbPAXY/eOqbHhKVz\n" +
                "                                                        Rq8/X+sto7IBzAImWeT5yQZ3N0JiiQCzyGUAEIrA6ChyZy9p/jnPop5g1s51NFtZ25Rpy+MBVgIA\n" +
                "                                                        soBLthbaLZHnS9nChqHX3Fk2sGbiEQeihSBaCMbSlYVLbUbJoAJ1TOC8RO4S5DO7nQV/AgLLnM5s\n" +
                "                                                        ZfVlIsnDdmCBLIbzIgAIHFfaiFeG/Ira6FbAh6SgOUsTWpmjZE5nvjKEGUeSlBdYrKIs5smCJBVz\n" +
                "                                                        ozEFig9NbZOc7QJt97GtMZSAc3PTvgycC8rmgq9H21H6XXkURB7ubofwtnot2wlRqK/WtpGZmVmS\n" +
                "                                                        mZmZrQxhZjPGki7AjAPyMOhoJVs0McbyUlJSmoqgCW25CZIzwBxl6LfF8pmZmTYAJmW+taXMsGmb\n" +
                "                                                        DrTy+9wRGHjDwhxwAD5AJXmgV1cDKsD3ow5skB8wymUMgc3Pg8q3hAQAKR/s6fX1d8f+E7GGVve5\n" +
                "                                                        Ix8BUTrcNqJvs/dSbtl3ejx8HFC3MArBAKZhKDlbW9VasFv2nLkOatbialspWoeuZu2m4OM//2Br\n" +
                "                                                        r+Ml9gmI1DRrk6pcgE6FVx8b12i1a+5u+2uo9wO1Lhgi1aVjrun62xuH90oeM6LLe5E66RSqXSCv\n" +
                "                                                        /9zK1BZioso6RPcxnih4PqHRYpNevaTafUsnr8aWXy33fjAr1xSpi6Fad+N4ajwwG9RnT72VmI71\n" +
                "                                                        j63GR3NXbPn9pI5tpN6wshVJcHtsDcfXzM4H2JRGZdbMzgdhfLMh0MAjIKEItdhnxioLGHM0Pha0\n" +
                "                                                        4GdKlgmMpYMx5Q9JygTxVGVV67ky01emKD1TAqKkRufARjXuPCrCh4/KHyalKRltq5DZTIEPJKlY\n" +
                "                                                        mQdzMKfTpnxxFjEgkZvNBWQ2L+Fmc1ajhSwy2QwwKfN4tsBqTkWwAvWLlcUshQDAOG/TQgXmdJYo\n" +
                "                                                        84dxFFhoI/sZ32o9zqcqj0NYIEmFQfElKsdtzOl0EJARwnYcARkXmt21EEOgnUKlH9O52WwPCCPj\n" +
                "                                                        XJ6PUhbxKAugipsOsSqLikJu4hzIHgPZelvmFgmwKoujWr2W7Q8aJ9pbxWq1WlJSUvKUTBDKIh6L\n" +
                "                                                        MuQZKBOvzBc2Fc+4QKaoZHLxAIqUYdCijIyMhcpQp6VJPRtjLN1qtZoUu1NacM+EczdORWgFJePM\n" +
                "                                                        A2BShLPp+RzGWKrVao1T2kzHpczcSflIANnV0NxRJ690DZxrIxIAnHRWv4AaN6A+z7CjywO9WVux\n" +
                "                                                        dOYNW5ue6m1SE+p9LQoLA0BuH4zRmvmtNaGSJAl+hLZDBKYGRo30Hg0+bPuubAW8BKZSNe6fGg9A\n" +
                "                                                        HJPvGnT/vDvj9gTO/W7V9/0OH66YCY8HY+J7r//97LGDd/zh/t9tffmenB2vTn7ifxJHDpg0fnAq\n" +
                "                                                        iPuo1t1idkk+Ajx+zJ94bat3gXf93xddvjtwNhExTRbHeXwY3D3yIrwMm2wgcoTYAKCk2WMhbs9U\n" +
                "                                                        ECUpAlggrx5tZdjV5U4A2JRG5YmS4HLPbVSOowgzVuZh+ooCRcQyG7LU3FklcjbIsxps6LRZYCxf\n" +
                "                                                        Pv9oNhgdbWhDq00HqKRFn3Jn2QCe2erK2FC9BBQxzs8tYuE8geQ/4njIz91ZGNDoCzg462j65RxU\n" +
                "                                                        39SQScorUfPb6pMievns3NxSdpO51eZ1nM4ixvloksuWKLE5lMUwowMrYZU5xTQERFW+u08LzDV2\n" +
                "                                                        FkHtQOnHZMhzvw7F33xwPjeQWUKZ2w08lsE4XxjIktF4SDg4ZofS13KG2tojHZxnKDcgJgBxgRuf\n" +
                "                                                        1q5lO3G1t4IiKkVK5kfKcGR+YIgSwBLGWCFjrDDEatQSxtgUpV4qEU3NzMx0MMbGp6SkFKakpBQE\n" +
                "                                                        hjyDKylDpjbGmF2xezSUb0S0kDFWELBznjiyAcQRUWYr5/OVWOwB+x3o6wvDBahGuKEa7jonlO2A\n" +
                "                                                        peYVxqV9sKsYnIHpWt76lSQGnK6B5ZaeGbaXJjUL9J/7z/aZ/PS6E6j3A0YdoG4+DEoAUONGVBd9\n" +
                "                                                        1vgR3V+fcNvgw4tu6edKBVgaYxwAsgqK4+a+ZiuGj4BoLaCSGjSTiMC8fiTc0GXWV2n/sRoARj3z\n" +
                "                                                        ScYPO05a0S1Knv/z+wG3F2ASusfqtt1/Y5+f5/z69qJgPya/9tXf/vnO1rk3PTxiw3evPHBfSzE/\n" +
                "                                                        9PrmQd/sOpVeVl77ILRquX+CFhdReTVuHn/NB9+mWh5rrZNTs4p1OVuLkkpOOA0wRMjjr26fqkdX\n" +
                "                                                        vfPP9zycc8l38bnYtGPXnCsNMhrjIEnFBDgku90cbn8EF05nXUt+BJukQbgz3PGEA2XYtTAjI+OS\n" +
                "                                                        /i3wKlQwILZ9tRjAqN1vByGgkk18sWDxhoKDL8J0nkU89T4wrQornx4/ZNbtAw6HKrJo9Q93vr/u\n" +
                "                                                        x/RKD8b4OQF1XoD7Ab38fjBJFwHu9gJ1bkCjhlavgcfvx9J5N4946v6hPwbszPzTxgc2Fp5KrXB7\n" +
                "                                                        RnnOdolAvRvQnwYiDQCXg+3SNcpeUVNvht0FqJi8249ODUOE+nQvs35bhFZatve1SWub+rg095h+\n" +
                "                                                        0bJ/1UHDawqemWduy044E178bNFXheWvweUHTDq5oyvqENVVv79m1SPDOu3q/1T4iQolN5uzIGeZ\n" +
                "                                                        cbhYu7wILgmdeS35YbwtDcaT4Y7pUmO1WlOUjPO8GyV0NrwKVQyIvhRtEVCtnnV377+V2qt8TjfX\n" +
                "                                                        aSNUISel/X5iWr/PP3xgVF5LIgkAS2eO2gRgbPKb3/fbcbA03mSMGmiM1kbtPebQON0+1dmzVZoB\n" +
                "                                                        PaPdep2Juz2+yOpaf7RG5auPcfuOBdtZ/d/j1wNYP+I3h3s8dE/+g7HG6LEFR+47dLB0t8HHOU5V\n" +
                "                                                        u80Vpyr7dutldEZ1N5T27RLj9bjdlV0j2S6Dp993H6aNaDG5/vTQ4VdQUYXUJffe09bt4r767X1L\n" +
                "                                                        k/767y83FZa/U1xsvwlMhegeXW3X9DEkXjYv57ycYJ22MOSygimPEBCQLQmRvKLp1GvJcNm9wegS\n" +
                "                                                        YSMiW6esWm0/5STvt3MpsHfcxEWG88TxnD/ReXsp3ruMbvjNl2sutPqkV79NvHPx5vvD3S8CgUAg\n" +
                "                                                        EAAA+Il7fkV2q6kzbD2UseXXmLisnSPUoSFKlYiorfsUCAQCgeAK5bJ+HyUvf+4a8lb3ZObGD7Fe\n" +
                "                                                        KJ9sO/b83MT4Do+lc/eLN9DpM5PD3T8CgUAguPhc3kLpK58E7m73hsOhePb9bRNcbk9k1pM3LumQ\n" +
                "                                                        T3xDJC8/eDOibvm+6V6zAoFAIPjpcVkLJXNtHc3UPcs6bgnYfKDiqVuv7f6nDhsq33q3StO/WIqa\n" +
                "                                                        0yl+CQQCgeDy5rIWSviqBjH1NUc6auaF94sGOurd6qTEri92xA45PhrE+d6erOfL/wp31wgEAoHg\n" +
                "                                                        0qDuuImLA5U+NYyq340nrXZbR23F9DS750TTLxaMG33By4mJCtT8yHNJvphBq8LdNwKBQCAAUEDx\n" +
                "                                                        YNR0m0MHiOUgQdnpy8YLABTBIi1sUjcZjJJAWIsEKaP5cbYQCawIuIyF0u/7PoER+VRd/9DufRSb\n" +
                "                                                        8uy9A0521AaVr3qStH326Lqt2tdRWwKBQCDoFEzyix6awJCIApoqiyWzhNyOh9EA+QUSsKCAipDA\n" +
                "                                                        bI2OB23eftkOvTIYRhB5K8LtBwDwskWTqWZbH3Xfv+eG2xeBQCAQNIWWwMIYLIyBlJeQM0pqc3VG\n" +
                "                                                        eSggU0unL1uhRP3u2yTD2DPhdoNX5PXjtevmsG5JS8Pti0AgEAjOS5Hyr6ltxakIYCYwavENRJet\n" +
                "                                                        UEpUOQoeR0m4/aDyuW9LMYlvSDHWsIu2QCAQCEKSDBsvgI0XgFExAIBY294IQ2yhIpbxsPGQr3W7\n" +
                "                                                        LOco+bGH9ajbALh/rAmnH94jQ1dA1adQ6v7qpo5bEwgEAsHFgcWh4ZVs5ABoCRKk7DZXJzYVjAoB\n" +
                "                                                        liKLZmMuS6GEfqAZNfUAu5A3h3UO/uIb0sjn66oaZJsd7u4QCAQCQWvQElikC991LYGVoIDmgiEP\n" +
                "                                                        YPFNT1+eQuk+GQ2fHzDeowK+uOTN89KUJ7gzY45q+O5hwIhw94ZAIBAIOka88piIDLGcZithE1g+\n" +
                "                                                        bDxDziobc1nOUXpr8rUAAT5790vdtq/smelkz1gm9V31AGMjwpbRCgQCgaCzYCb5kQ/lw2hAyGIW\n" +
                "                                                        aWGoodfLEn72rzfTDyD/vq6Fl7Jdb3Gfh3zbQbz8navybeUCgUAguELgzjduop0g/16dh0rnGy5F\n" +
                "                                                        m97yxffznSBv6ZOJ4Y5fIBAIBIJW4fswkn4E0Y8gfvLJ4Re7Pd+xSY/wHSBf+VOPhzt2gUAgEAjO\n" +
                "                                                        C5XOGkZ7JKKdIF/pI49czLb48UcW8kKQ7/BNT4U7boFAIBAI2gQvW9Sd/6jyURHIf3jM6ovVjv+g\n" +
                "                                                        +U2+A+Q7MmFBuGMWCAQCgaDNEKVKfJ/pFO0E+fdoavjZzJjOtM/PPBjt39e3wL8T5Dv64GPhjlcg\n" +
                "                                                        EAgEgnbjPXDtKr4LRDtBvPiejr9wOWD35KP38V0gXgTynrTeH+44BQKBQCC4IPiJaUm0E0R7QP6d\n" +
                "                                                        IH76lVs7ZO/k3G7+g3HLeRHIv6/rWSqdNSzcMQoEAoFAcMHwUxk9/Lvgpx9BfBfIt7d7PS9/7pr2\n" +
                "                                                        2qHT06L4kZ8949+l4XwHyH9wxD+J1qjCHZ9AIBAIBB2GH71rGRWCaK8ki+Vu1PGSKfPaUre+bEac\n" +
                "                                                        78iNi/17Ik5RoTyE6yu5f3G4YxIIBALBlQULtwOtQaV/6eqvWFCuksCgkgA/Bwjgmh6FpBu+VsXY\n" +
                "                                                        OmJdTkEzwQt/vtbnPdZXzWMnEFXdAfe+eySfTyIApOu6m0WOXyD1/XhLuGMSCAQCwZXFZS2UAOA7\n" +
                "                                                        9V+zpVN/Xs7UACQVAA74lc1sVQwcERxQeRnzacH9APcDBDAC/Cq4EH33S+oB/3o53HEIBAKB4Mrk\n" +
                "                                                        shdKAPCdmPkrqXL1G4xB3sadKW6TIpiBTeCVf/0qjY/pbsx0aQZmRPZbcSLc/gsEAoHgyuWKEEoA\n" +
                "                                                        4Mdn3sTrv/kj8x69g5GilRSkkUznZ6puu7l+YJYkdflY6pcnBFIgEAgEHeaKEcoA7tJfjdHUbb6N\n" +
                "                                                        yNFD0gxQk7+qBpL6GDQDvpH6/f1guP0TCAQCwU+L/wfCr7o1fidH1AAAACV0RVh0ZGF0ZTpjcmVh\n" +
                "                                                        dGUAMjAyMi0xMC0yNlQwODowOTo0NyswMDowMCf9pcQAAAAldEVYdGRhdGU6bW9kaWZ5ADIwMjIt\n" +
                "                                                        MTAtMjZUMDg6MDk6NDcrMDA6MDBWoB14AAAAKHRFWHRkYXRlOnRpbWVzdGFtcAAyMDIyLTEwLTI2\n" +
                "                                                        VDA4OjA5OjQ3KzAwOjAwAbU8pwAAAABJRU5ErkJggg==\" />\n" +
                "                                                        </svg>\n" +
                "                                                    </div>\n" +
                "                                                        <!-- <img alt=\"your-logo\" src=\"images/foto.png\" style=\"display:block;height:auto;border:0;width:320px;max-width:100%\" title=\"your-logo\" width=\"200\"/> -->\n" +
                "                                                    </div>\n" +
                "                                                </td>\n" +
                "                                            </tr>\n" +
                "                                        </table>\n" +
                "                                    </td>\n" +
                "                                </tr>\n" +
                "                            </tbody>\n" +
                "                        </table>\n" +
                "                    </td>\n" +
                "                </tr>\n" +
                "            </tbody>\n" +
                "        </table>\n" +
                "        <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"row row-4\" role=\"presentation\" style=\"mso-table-lspace:0;mso-table-rspace:0;background-color:#f7f6f5\" width=\"100%\">\n" +
                "            <tbody>\n" +
                "                <tr>\n" +
                "                    <td>\n" +
                "                        <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"row-content stack\" role=\"presentation\" style=\"mso-table-lspace:0;mso-table-rspace:0;background-color:#fff;color:#000;width:600px\" width=\"600\">\n" +
                "                            <tbody>\n" +
                "                                <tr>\n" +
                "                                    <td class=\"column column-1\" style=\"mso-table-lspace:0;mso-table-rspace:0;font-weight:400;text-align:left;vertical-align:top;padding-top:0;padding-bottom:0;border-top:0;border-right:0;border-bottom:0;border-left:0\" width=\"100%\">\n" +
                "                                        <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"text_block block-2\" role=\"presentation\" style=\"mso-table-lspace:0;mso-table-rspace:0;word-break:break-word\" width=\"100%\">\n" +
                "                                            <tr>\n" +
                "                                                <td class=\"pad\" style=\"padding-top:40px;padding-right:15px;padding-bottom:40px;padding-left:15px\">\n" +
                "                                                    <div style=\"font-family:Tahoma,Verdana,sans-serif\">\n" +
                "                                                        <div class=\"\" style=\"font-size:12px;font-family:Lato,Tahoma,Verdana,Segoe,sans-serif;mso-line-height-alt:18px;color:#222;line-height:1.5\">\n" +
                "                                                            <p style=\"margin:0;font-size:16px;text-align:center;mso-line-height-alt:24px\">\n" +
                "                                                                <span style=\"font-size:16px;\">\n" +
                "                                                                    <strong>Hai " + username + "</strong>\n" +
                "                                                                </span>\n" +
                "                                                            </p>\n" +
                "                                                            <p style=\"margin:0;font-size:14px;text-align:center;mso-line-height-alt:24px\">\n" +
                "                                                                <span style=\"font-size:14px;\">Terima kasih telah melakukan login ke MIMs Application. Silahkan masukkan kode OTP dibawah ini, untuk melanjutkan proses login. Kode OTP ini valid untuk <strong>5 menit</strong>\n" +
                "                                                                </span>\n" +
                "                                                            </p>\n" +
                "                                                        </div>\n" +
                "                                                    </div>\n" +
                "                                                </td>\n" +
                "                                            </tr>\n" +
                "                                        </table>\n" +
                "                                    </td>\n" +
                "                                </tr>\n" +
                "                            </tbody>\n" +
                "                        </table>\n" +
                "                    </td>\n" +
                "                </tr>\n" +
                "            </tbody>\n" +
                "        </table>\n" +
                "        <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"row row-3\" role=\"presentation\" style=\"mso-table-lspace:0;mso-table-rspace:0;background-color:#f7f6f5\" width=\"100%\">\n" +
                "            <tbody>\n" +
                "                <tr>\n" +
                "                    <td>\n" +
                "                        <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"row-content stack\" role=\"presentation\" style=\"mso-table-lspace:0;mso-table-rspace:0;background-color:#fff;color:#000;width:600px\" width=\"600\">\n" +
                "                            <tbody> \n" +
                "                                <tr>\n" +
                "                                    <td class=\"column column-1\" style=\"mso-table-lspace:0;mso-table-rspace:0;font-weight:400;text-align:left;vertical-align:top;padding-top:0;padding-bottom:0;border-top:0;border-right:0;border-bottom:0;border-left:0\" width=\"100%\">                                                    \n" +
                "                                        <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"heading_block block-3\" role=\"presentation\" style=\"mso-table-lspace:0;mso-table-rspace:0\" width=\"100%\">\n" +
                "                                            <tr>\n" +
                "                                                <td class=\"pad\" style=\"width:100%;text-align:center;\">\n" +
                "                                                    <h5 style=\"margin:0;color:#072b52;font-size:35px;font-family:Lora,Georgia,serif;line-height:120%;text-align:center;direction:ltr;font-weight:400;letter-spacing:1px;margin-top:0;margin-bottom:0\">\n" +
                "                                                        <strong>" + token + "</strong>\n" +
                "                                                    </h5>\n" +
                "                                                </td>\n" +
                "                                            </tr>\n" +
                "                                        </table>\n" +
                "                                    </td>\n" +
                "                                </tr>\n" +
                "                            </tbody>\n" +
                "                        </table>\n" +
                "                    </td>\n" +
                "                </tr>\n" +
                "            </tbody>\n" +
                "        </table>\n" +
                "        <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"row row-4\" role=\"presentation\" style=\"mso-table-lspace:0;mso-table-rspace:0;background-color:#f7f6f5\" width=\"100%\">\n" +
                "            <tbody>\n" +
                "                <tr>\n" +
                "                    <td>\n" +
                "                        <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"row-content stack\" role=\"presentation\" style=\"mso-table-lspace:0;mso-table-rspace:0;background-color:#fff;color:#000;width:600px\" width=\"600\">\n" +
                "                            <tbody>\n" +
                "                                <tr>\n" +
                "                                    <td class=\"column column-1\" style=\"mso-table-lspace:0;mso-table-rspace:0;font-weight:400;text-align:left;vertical-align:top;padding-top:0;padding-bottom:0;border-top:0;border-right:0;border-bottom:0;border-left:0\" width=\"100%\">\n" +
                "                                        <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"text_block block-2\" role=\"presentation\" style=\"mso-table-lspace:0;mso-table-rspace:0;word-break:break-word\" width=\"100%\">\n" +
                "                                            <tr>\n" +
                "                                                <td class=\"pad\" style=\"padding-top:40px;padding-right:15px;padding-bottom:40px;padding-left:15px\">\n" +
                "                                                    <div style=\"font-family:Tahoma,Verdana,sans-serif\">\n" +
                "                                                        <div class=\"\" style=\"font-size:12px;font-family:Lato,Tahoma,Verdana,Segoe,sans-serif;mso-line-height-alt:18px;color:#222;line-height:1.5\">\n" +
                "                                                            <p style=\"margin:0;font-size:14px;text-align:center;mso-line-height-alt:24px\">\n" +
                "                                                                <span style=\"font-size:14px;\">JANGAN berikan kode OTP tersebut ke pihak manapun. Apabila anda tidak merasa melakukan login, anda dapat mengabaikan email ini.\n" +
                "                                                                </span>\n" +
                "                                                            </p>\n" +
                "                                                        </div>\n" +
                "                                                    </div>\n" +
                "                                                </td>\n" +
                "                                            </tr>\n" +
                "                                        </table>\n" +
                "                                    </td>\n" +
                "                                </tr>\n" +
                "                            </tbody>\n" +
                "                        </table>\n" +
                "                    </td>\n" +
                "                </tr>\n" +
                "            </tbody>\n" +
                "        </table>\n" +
                "        <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"row row-8\" role=\"presentation\" style=\"mso-table-lspace:0;mso-table-rspace:0;background-color:#f7f6f5\" width=\"100%\">\n" +
                "            <tbody>\n" +
                "                <tr>\n" +
                "                    <td>\n" +
                "                        <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"row-content stack\" role=\"presentation\" style=\"mso-table-lspace:0;mso-table-rspace:0;background-color:#eee;color:#000;width:600px\" width=\"600\">\n" +
                "                            <tbody>\n" +
                "                                <tr>\n" +
                "                                    <td class=\"column column-1\" style=\"mso-table-lspace:0;mso-table-rspace:0;font-weight:400;text-align:left;vertical-align:top;padding-top:5px;padding-bottom:5px;border-top:0;border-right:0;border-bottom:0;border-left:0\" width=\"100%\">\n" +
                "                                        <table border=\"0\" cellpadding=\"10\" cellspacing=\"0\" class=\"text_block block-1\" role=\"presentation\" style=\"mso-table-lspace:0;mso-table-rspace:0;word-break:break-word\" width=\"100%\">\n" +
                "                                            <tr>\n" +
                "                                                <td class=\"pad\">\n" +
                "                                                    <div style=\"font-family:Tahoma,Verdana,sans-serif\">\n" +
                "                                                        <div class=\"\" style=\"font-size:12px;font-family:Lato,Tahoma,Verdana,Segoe,sans-serif;mso-line-height-alt:14.399999999999999px;color:#f7f6f5;line-height:1.2\">\n" +
                "                                                            <p style=\"margin:0;text-align:center;mso-line-height-alt:14.399999999999999px\">\n" +
                "                                                                <a href=\"http://www.example.com/\" rel=\"noopener\" style=\"text-decoration: underline; color: #000;\" target=\"_blank\" title=\"http://www.example.com/\">@2022\n" +
                "                                                                </a>\n" +
                "                                                            </p>\n" +
                "                                                        </div>\n" +
                "                                                    </div>\n" +
                "                                                </td>\n" +
                "                                            </tr>\n" +
                "                                        </table>\n" +
                "                                    </td>\n" +
                "                                </tr>\n" +
                "                            </tbody>\n" +
                "                        </table>\n" +
                "                    </td>\n" +
                "                </tr>\n" +
                "            </tbody>\n" +
                "        </table>\n" +
                "    </main>\n" +
                "   </body>\n" +
                "</html>";

        mail.setContent(message,"text/html");
        javaMailSender.send(mail);


    }
}
