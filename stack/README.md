[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/6YV_Encp)
# Zadanie Stos i RPN


| Termin oddania      | Punkty     |
|---------------------|:-----------|
|    28.03.2025 23:00 |   10       |

--- 
Przekroczenie terminu o **n** zajęć wiąże się z karą:
- punkty uzyskane za realizację zadania są dzielone przez **2<sup>n</sup>**.

--- 

## Zadanie 1: Stos

### 01 Green
Zaimplementuj klasę ``Stack`` implementującą ideę stosów napisów z następującymi metodami publicznymi:
- ``push`` wkłada jeden element na stos
- ``pop`` zdejmuje jeden element ze stosu i oddaje wartość tego elementu; co się ma dziać gdy, ``pop`` próbuje 
    zdjąc element z pustego stosu?
- ``peek`` podobnie jak ``pop`` oddaje wartość elementu na szczycie stosu ale go nie zdejmuje; 
    podobny problem z pustym stosem co w przypadku ``pop``.
Podstawową strukturą danych w implementacji stosu powinna być tablica. Stos nie powinien posiadać ograniczeń rozmiaru.

### 00 Red
Przygotuj testy jednostkowe dla klasy ``Stack`` zanim przystąpisz do implementacji tej klasy.

## Zadanie 2: RPN

### Green
Zaimplementuj klasę wyliczającą wyrażenia arytmetyczne zapisane w [Odwrotnej Notacji Polskiej](https://pl.wikipedia.org/wiki/Odwrotna_notacja_polska).
Założenia:
- wyrażenia są ciągami znaków
- program umożliwia wyliczanie wyrażeń złożonych z liczb całkowitych i operacji binarnych takich jak ``+``, ``-`` czy ``*``.
- do implementacji wykorzystaj klasę ``Stack`` z Zadania 1.

### Red
Przygotuj testy jednostkowe dla implementacji RPN.

---

## UWAGA: 
Staraj się zastosować zasady [SOLID](https://www.samouczekprogramisty.pl/solid-czyli-dobre-praktyki-w-programowaniu-obiektowym/)
i [Clean Code](https://cleancoders.com/episode/clean-code-episode-1).
