package com.test.model;

import java.util.Date;
import java.util.Objects;

public class Query {
    //C service_id[.variation_id] question_type_id[.category_id.[sub-category_id]] P/N date time
    private Long serviceId;
    private Long questionTypeId;
    private String answer;
    private Date date;
    private Long seconds;

    public Query() {

    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public Long getQuestionTypeId() {
        return questionTypeId;
    }

    public void setQuestionTypeId(Long questionTypeId) {
        this.questionTypeId = questionTypeId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getSeconds() {
        return seconds;
    }

    public void setSeconds(Long seconds) {
        this.seconds = seconds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Query query = (Query) o;

        if (!Objects.equals(serviceId, query.serviceId)) return false;
        if (!Objects.equals(questionTypeId, query.questionTypeId))
            return false;
        if (!Objects.equals(answer, query.answer)) return false;
        if (!Objects.equals(date, query.date)) return false;
        return Objects.equals(seconds, query.seconds);
    }

    @Override
    public int hashCode() {
        int result = serviceId != null ? serviceId.hashCode() : 0;
        result = 31 * result + (questionTypeId != null ? questionTypeId.hashCode() : 0);
        result = 31 * result + (answer != null ? answer.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (seconds != null ? seconds.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Query{" +
                "serviceId=" + serviceId +
                ", questionTypeId=" + questionTypeId +
                ", answer='" + answer + '\'' +
                ", date=" + date +
                ", seconds=" + seconds +
                '}';
    }
}
