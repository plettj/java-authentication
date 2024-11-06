package Assignment_2.src.app.src.main.java;

/**
 * PrintJob represents an individual job in the print queue with a unique ID and
 * filename.
 */
public class PrintJob {
    private final int jobId;
    private final String filename;

    /**
     * Constructor for a PrintJob.
     *
     * @param jobId    The unique ID of the job.
     * @param filename The name of the file to print.
     */
    public PrintJob(int jobId, String filename) {
        this.jobId = jobId;
        this.filename = filename;
    }

    public int getJobId() {
        return jobId;
    }

    public String getFilename() {
        return filename;
    }

    @Override
    public String toString() {
        return jobId + " " + filename;
    }
}
