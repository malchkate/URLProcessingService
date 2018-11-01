package com.segmento.demoService;

import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ConcurrentSkipListSet;

@Component
public class UrlContainer {
    private final static ConcurrentMap<String, Long> urlMap;
    private final static ConcurrentSkipListMap<Long, ConcurrentSkipListSet<String>> frequenciesAndDomainSetsMap;
    private final static ConcurrentSkipListMap<Long, Long> frequenciesAndAmountOfDomainsInSetsMap;

    static {
        urlMap = new ConcurrentHashMap<>();
        frequenciesAndDomainSetsMap = new ConcurrentSkipListMap<>((e1, e2) ->(e2.compareTo(e1)));
        frequenciesAndAmountOfDomainsInSetsMap = new ConcurrentSkipListMap<>((e1, e2) ->(e2.compareTo(e1)));
    }

    public synchronized void addToContainer(String secondLevelDomain){
        long frequency = urlMap.getOrDefault(secondLevelDomain, (long)0);
        urlMap.put(secondLevelDomain, frequency + 1);

        Optional<ConcurrentSkipListSet<String>> oldSetOptional = Optional.ofNullable(frequenciesAndDomainSetsMap.get(frequency));
        oldSetOptional.ifPresent((list) -> list.remove(secondLevelDomain));

        long amountOfElementsInOldSet = frequenciesAndAmountOfDomainsInSetsMap.getOrDefault(frequency, (long)0);
        if(amountOfElementsInOldSet > 0){
            frequenciesAndAmountOfDomainsInSetsMap.put(frequency, amountOfElementsInOldSet - 1);
        }

        ConcurrentSkipListSet<String> newSet = frequenciesAndDomainSetsMap.getOrDefault(frequency + 1, new ConcurrentSkipListSet<>());
        newSet.add(secondLevelDomain);
        frequenciesAndDomainSetsMap.put(frequency+1, newSet);

        long amountOfElementsInNewSet = frequenciesAndAmountOfDomainsInSetsMap.getOrDefault(frequency + 1, (long)0);
        frequenciesAndAmountOfDomainsInSetsMap.put(frequency + 1, amountOfElementsInNewSet + 1);

    }

    public List<String> getDataFromContainer(int n){
        TreeMap<Long, Long> amountsMap = new TreeMap<>(frequenciesAndAmountOfDomainsInSetsMap);
        if (amountsMap.isEmpty()){
            return new ArrayList<>(0);
        }
        long indexOfFirstSetToBeTaken = amountsMap.firstKey();
        long indexOfLastSetToBeTaken = indexOfFirstSetToBeTaken;
        int sumOfSetsElementsTemp = 0;
        for (Map.Entry<Long, Long> entry: amountsMap.entrySet()) {
            indexOfLastSetToBeTaken = entry.getKey();
            sumOfSetsElementsTemp += entry.getValue();
            if (sumOfSetsElementsTemp >= n){
                break;
            }
        }
        Map<Long,ConcurrentSkipListSet<String>> setsMap = new TreeMap<>(frequenciesAndDomainSetsMap.subMap(indexOfFirstSetToBeTaken,true, indexOfLastSetToBeTaken, true));
        List<String> domainsList = new ArrayList<>();
        int amountOfDomainsTaken = 0;
        for (Map.Entry<Long,ConcurrentSkipListSet<String>> entry: setsMap.entrySet()) {
            for (String domain: entry.getValue()) {
                if (amountOfDomainsTaken >= n){
                    break;
                }
                domainsList.add(domain);
                amountOfDomainsTaken++;
            }
        }
        return domainsList;
    }


}
