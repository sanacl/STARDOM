package eu.alertproject.iccs.stardom.alertconnector.api;

import eu.alertproject.iccs.stardom.analyzers.its.bus.ItsChangeEvent;
import eu.alertproject.iccs.stardom.analyzers.its.connector.ItsCommentConnectorContext;
import eu.alertproject.iccs.stardom.analyzers.its.connector.ItsChangeConnectorContext;
import eu.alertproject.iccs.stardom.bus.api.Bus;
import eu.alertproject.iccs.stardom.connector.api.ConstructorConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * User: fotis
 * Date: 27/08/11
 * Time: 18:57
 */
@Controller
public class ItsHistoryController implements ConstructorConnector<ItsChangeConnectorContext> {

    private Logger logger = LoggerFactory.getLogger(ItsCommentConnectorContext.class);

    private AtomicInteger messageCount = new AtomicInteger();

    @RequestMapping(value = "/constructor/action/its/history", method = RequestMethod.POST)
    public @ResponseBody
    void action(@RequestBody ItsChangeConnectorContext context) {

        logger.trace("void istAction() {} {}",context.getAction());

        //create the action
        ItsChangeEvent itsEvent= new ItsChangeEvent(this,context);

        messageCount.incrementAndGet();
        Bus.publish(itsEvent);

    }

    @RequestMapping(value = "/constructor/itshistory/count", method = RequestMethod.GET)
    public @ResponseBody Integer getMessageCount(){

        return messageCount.get();

    }
}
