package com.otarbakh.rickyandmorty.domain.use_case

//class LocationsUseCase @Inject constructor(
//    private val rickAndMortyRepository: RickAndMortyRepository
//) {
//    fun getLocations(q:String): Flow<PagingData<LocationsDomain>>  {
//        return rickAndMortyRepository.getLocations(q)
//            .flow
//            .map { pagingData -> pagingData.map { it.toDomain() } }
//    }
//}