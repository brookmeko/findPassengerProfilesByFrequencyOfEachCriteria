   public List<ReportPassengerRiskDTO> findPassengerProfilesByFrequencyOfEachCriteria(Date startDate, Date endDate) {
        return passengerProfileRepository.findPassengerProfilesByFrequencyOfEachCriteria(  startDate,
                endDate);
    }
