package eu.alertproject.iccs.stardom.alertconnector.api;

import eu.alertproject.iccs.stardom.activemqconnector.internal.*;
import eu.alertproject.iccs.stardom.analyzers.its.bus.ItsCommentEvent;
import eu.alertproject.iccs.stardom.bus.api.Event;
import eu.alertproject.iccs.stardom.bus.api.annotation.EventHandler;
import org.bushe.swing.event.annotation.EventSubscriber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * User: fotis
 * Date: 13/11/11
 * Time: 22:29
 */
@Controller
@EventHandler
public class EventCountController {


    private Logger logger = LoggerFactory.getLogger(EventCountController.class);

    
    Map<String,Integer> counters;

//    @Autowired
//    MailNewMailListener mailNewMailListener;
//
//    @Autowired
//    ItsNewCommentListener itsNewCommentListener;
//
//    @Autowired
//    ItsNewIssueListener itsNewIssueListener;
//
//    @Autowired
//    ItsHistoryListener itsHistoryListener;
//
//
//    @Autowired
//    ScmNewCommitListener scmNewCommitListener;
//
    @RequestMapping(value = "/constructor/events/count", method = RequestMethod.GET)
    public @ResponseBody Map<String,Integer> getCount(){


//        Map<String,Integer> ret = new HashMap<String, Integer>();
//
//        ret.put("ml",mailNewMailListener.getMessageCount());
//        ret.put("its",itsNewCommentListener.getMessageCount()+itsNewIssueListener.getMessageCount()+itsHistoryListener.getMessageCount());
//        ret.put("scm",scmNewCommitListener.getMessageCount());
//
//
        logger.trace("Map<String,Integer> getCount() {} ",counters);
        return counters;

    }

    
    @PostConstruct
    public void init(){
        logger.trace("void init()");
        counters = new LinkedHashMap<String, Integer>();
    }

    @EventSubscriber(eventClass = Event.class)
    public void countEvent(Event event){

        String classString = event.getClass().getName();

        if(!counters.containsKey(classString)){
            counters.put(classString,0);            
        }

        counters.put(classString, counters.get(classString) + 1);



    }



}
