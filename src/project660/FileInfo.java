package project660;

import java.io.Serializable;
import java.util.Date;

/**
 * Contains information about the graph
 * 
 * @author Mike Gordo <himor.cre@gmail.com>
 */
public class FileInfo implements Serializable
{
    private static final long serialVersionUID = 1L;
	
	protected String filename;
	protected String filepath;
	protected Date date;
	protected Boolean locked;
	protected String report;
	protected String fullreport;
	
	public FileInfo()
	{
		this.filename   = "";
		this.filepath   = "";
		this.date       = new Date();
		this.locked     = false;
		this.report     = "";
		this.fullreport = "";
	}
	
	public void loadFileInfo(FileInfo fi)
	{
		this.filename   = fi.filename;
		this.filepath   = fi.filepath;
		this.date       = fi.date;
		this.locked     = fi.locked;
		this.report     = fi.report;
		this.fullreport = fi.fullreport;
	}

	public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }

    public String getFullreport() {
        return fullreport;
    }

    public void setFullreport(String fullreport) {
        this.fullreport = fullreport;
    }
    
}
