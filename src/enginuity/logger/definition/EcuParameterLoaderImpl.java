package enginuity.logger.definition;

import enginuity.logger.exception.ConfigurationException;
import static enginuity.util.ParamChecker.checkNotNullOrEmpty;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.InputStream;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.List;

public final class EcuParameterLoaderImpl implements EcuParameterLoader {

    public List<EcuParameter> loadFromXml(String loggerXmlPath) {
        checkNotNullOrEmpty(loggerXmlPath, "loggerXmlPath");
        try {
            InputStream inputStream = new BufferedInputStream(new FileInputStream(loggerXmlPath));
            try {
                SAXParser parser = getSaxParserFactory().newSAXParser();
                LoggerDefinitionHandler handler = new LoggerDefinitionHandler();
                parser.parse(inputStream, handler);
                return handler.getEcuParameters();
            } finally {
                inputStream.close();
            }
        } catch (Exception e) {
            throw new ConfigurationException(e);
        }
    }

    private SAXParserFactory getSaxParserFactory() {
        SAXParserFactory parserFactory = SAXParserFactory.newInstance();
        parserFactory.setNamespaceAware(false);
        parserFactory.setValidating(false);
        parserFactory.setXIncludeAware(false);
        return parserFactory;
    }

}