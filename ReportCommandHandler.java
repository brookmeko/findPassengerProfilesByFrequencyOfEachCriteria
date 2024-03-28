    public List<ReportPassengerRiskDTO> findPassengerProfilesByFrequencyOfEachCriteria(Date startDate, Date endDate) {
        return reportReceivedHelper.findPassengerProfilesByFrequencyOfEachCriteria(  startDate,
                endDate);
    }
