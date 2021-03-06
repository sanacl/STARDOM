package eu.alertproject.iccs.stardom.analyzers.scm.constructor;

import eu.alertproject.iccs.stardom.analyzers.scm.connector.ScmAction;
import eu.alertproject.iccs.stardom.analyzers.scm.connector.ScmFile;
import eu.alertproject.iccs.stardom.datastore.api.dao.PathSignatureHistoryDao;
import eu.alertproject.iccs.stardom.domain.api.Identity;
import eu.alertproject.iccs.stardom.domain.api.PathSignatureHistory;
import eu.alertproject.iccs.stardom.domain.api.metrics.ScmApiIntroducedMetric;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * User: fotis
 * Date: 17/07/11
 * Time: 14:07
 */
public class ScmApiIntroducedAnalyzer extends AbstractScmAnalyzer {

    private Logger logger = LoggerFactory.getLogger(ScmApiIntroducedAnalyzer.class);
 
    @Autowired
    PathSignatureHistoryDao pathSignatureHistoryDao;

    @Override
    @Transactional
    public void analyze(Identity identity, ScmAction action) {

        if(identity ==null){
            return;
        }

        List<PathSignatureHistory> newFiles = new ArrayList<PathSignatureHistory>();
        
        //check if the api has been introduced before
        List<ScmFile> files = action.getFiles();
        
        for(ScmFile sf: files){

            String path = sf.getName();



            List<String> functions = sf.getFunctions();
            for(String signature: functions) {

                PathSignatureHistory byIdentityPathAndSignature = pathSignatureHistoryDao.findByIdentityPathAndSignature(
                        identity,
                        path,
                        signature);
                
                
                if(byIdentityPathAndSignature == null ){
                    
                    List<ScmApiIntroducedMetric> sqmForIdenity = getMetricDao().<ScmApiIntroducedMetric>getForIdentity(identity,ScmApiIntroducedMetric.class);

                    logger.trace("void analyze() Handling {} -> {} ",identity.getUuid(),action.getDate());

                    ScmApiIntroducedMetric metric = null;
                    if(sqmForIdenity ==null || sqmForIdenity.size() <= 0 ){

                        metric = new ScmApiIntroducedMetric();
                        metric.setIdentity(identity);
                        metric.setQuantity(0);
                        metric = (ScmApiIntroducedMetric) getMetricDao().insert(metric);
                    }else{
                        metric = sqmForIdenity.get(0);
                    }

                    metric.setCreatedAt(action.getDate());
                    metric.increaseQuantity();

                    metric = (ScmApiIntroducedMetric) getMetricDao().update(metric);

                    logger.trace("void analyze() {} ",metric);


                    PathSignatureHistory pathSignatureHistory = new PathSignatureHistory();
                    pathSignatureHistory.setIdentity(identity);
                    pathSignatureHistory.setPath(path);
                    pathSignatureHistory.setSignature(signature);

                    pathSignatureHistoryDao.insert(pathSignatureHistory);

                }
            }

        }

    }
}
