    @Override
    public List<ReportPassengerRiskDTO> findPassengerProfilesByFrequencyOfEachCriteria(Date startDate, Date endDate) {
        return passengerProfileJpaRepository.findPassengerProfilesByFrequencyOfEachCriteria(  startDate,
                endDate);
    }
