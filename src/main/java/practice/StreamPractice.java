package practice;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import model.Candidate;
import model.Cat;
import model.Person;

public class StreamPractice {
    private static final int ZERO = 0;
    private static final int ONE = 1;
    private static final int TWO = 2;

    public int findMinEvenNumber(List<String> numbers) {
        return numbers.stream()
                .map(s -> s.split(","))
                .flatMap(Arrays::stream)
                .map(Integer::parseInt)
                .filter(i -> i % TWO == ZERO)
                .min(Integer::compare)
                .orElseThrow(() -> new RuntimeException("Can't get min value from list: "
                        + numbers));
    }

    public Double getOddNumsAverage(List<Integer> numbers) {
        return IntStream.range(ZERO, numbers.size())
                .map(i -> (i % TWO == ZERO) ? numbers.get(i) : numbers.get(i) - ONE)
                .filter(i -> i % TWO != ZERO)
                .average()
                .orElseThrow(() ->
                        new NoSuchElementException("No corresponding items "
                                + "found in the list: " + numbers));
    }

    public List<Person> selectMenByAge(List<Person> peopleList, int fromAge, int toAge) {
        return peopleList.stream()
                .filter(p -> p.getAge() >= fromAge
                        && p.getAge() <= toAge && p.getSex() == Person.Sex.MAN)
                .collect(Collectors.toList());
    }

    public List<Person> selectPeopleByAge(List<Person> people, int fromAge, int maleToAge,
                                          int femaleToAge) {
        return people.stream()
                .filter(p -> p.getSex() == Person.Sex.WOMAN
                        ? p.getAge() >= fromAge && p.getAge() <= femaleToAge
                        : p.getAge() >= fromAge && p.getAge() <= maleToAge)
                .toList();
    }

    public List<String> getCatNames(List<Person> peopleList, int femaleAge) {
        return peopleList.stream()
                .filter(p -> p.getAge() >= femaleAge && p.getSex() == Person.Sex.WOMAN)
                .map(Person::getCats)
                .flatMap(List::stream)
                .map(Cat::getName)
                .toList();
    }

    public List<String> getEligibleCandidates(List<Candidate> candidates) {
        return candidates.stream()
                .filter(new CandidateValidator())
                .map(Candidate::getName)
                .sorted()
                .toList();
    }
}
