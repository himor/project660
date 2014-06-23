<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="project660.*" %>

<%

Config config_ = Config.getInstance(); 
config_.setRootDir("C:\\temp");

Interactor i = new Interactor();

/* FileInfo fi = new FileInfo();
fi.setFilename("one");
fi.setLocked(false);
i.addToFileList(config_.rootDir, fi);

FileInfo fi2 = new FileInfo();
fi2.setFilename("two");
fi2.setLocked(false);
i.addToFileList(config_.rootDir, fi2);
 */
FileInfo fi2 = new FileInfo();
fi2.setFilename("ten");
fi2.setLocked(false);
i.addToFileList(config_.rootDir, fi2);



%>