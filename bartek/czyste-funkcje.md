### Tytuł: **Generowanie Pure Functions za pomocą GitHub Copilot**

#### Dlaczego to ważne:
Funkcje czyste (*pure functions*) są podstawą programowania funkcyjnego i dobrych praktyk w programowaniu imperatywnym. Charakteryzują się tym, że dla tych samych danych wejściowych zawsze zwracają te same wyniki, nie modyfikując stanu zewnętrznego. Dzięki temu kod staje się bardziej przewidywalny, łatwiejszy do testowania i debugowania.

---

#### Wady bez AI:
- **Czasochłonność**: Ręczne tworzenie funkcji wymaga precyzyjnego określenia logiki i wielu iteracji.
- **Podatność na błędy**: Nietypowe przypadki mogą zostać pominięte, co prowadzi do błędów w działaniu funkcji.
- **Ograniczona kreatywność**: Programiści mogą trzymać się utartych schematów, tracąc szansę na nowe podejścia.

---

#### Zalety z AI:
- **Przyspieszenie procesu**: Copilot generuje funkcje w kilka sekund na podstawie opisu wejść i wyjść.
- **Wsparcie dla różnych przykładów**: Można podać kilka przypadków testowych, a AI automatycznie dostosuje logikę funkcji.
- **Redukcja błędów**: AI sugeruje rozwiązania odporne na typowe błędy logiczne.
- **Elastyczność i różnorodność**: Copilot radzi sobie z różnymi typami transformacji, w tym operacjami na datach, adaptacjami danych czy konwersjami jednostek.

---


### Przykłady zaawansowanych operacji:

---

#### 1. **Zamiana drzewa binarnego na listę w porządku in-order**

**Opis problemu:**  
Zamień dane w drzewie binarnym na listę w porządku in-order (lewe poddrzewo → węzeł → prawe poddrzewo).

**Wejście (drzewo):**
```
      5
     / \
    3   7
   / \   \
  2   4   8
```

**Oczekiwane wyjście (lista):**
```java
[2, 3, 4, 5, 7, 8]
```

**Kod wygenerowany przez GitHub Copilot:**
```java
import java.util.ArrayList;
import java.util.List;

class TreeNode {
    int val;
    TreeNode left, right;
    TreeNode(int val) {
        this.val = val;
    }
}

public List<Integer> inOrderTraversal(TreeNode root) {
    List<Integer> result = new ArrayList<>();
    traverse(root, result);
    return result;
}

private void traverse(TreeNode node, List<Integer> result) {
    if (node == null) return;
    traverse(node.left, result);
    result.add(node.val);
    traverse(node.right, result);
}
```

---

#### 2. **Obracanie drzewa binarnego (mirror)**

**Opis problemu:**  
Odwróć drzewo binarne, zamieniając lewe i prawe poddrzewo każdego węzła.

**Wejście (drzewo):**
```
      5
     / \
    3   7
   / \   \
  2   4   8
```

**Oczekiwane wyjście (odwrócone drzewo):**
```
      5
     / \
    7   3
   /   / \
  8   4   2
```

**Kod wygenerowany przez GitHub Copilot:**
```java
public TreeNode mirrorTree(TreeNode root) {
    if (root == null) return null;
    TreeNode temp = root.left;
    root.left = mirrorTree(root.right);
    root.right = mirrorTree(temp);
    return root;
}
```

---

#### 3. **Przekształcenie drzewa binarnego na zagnieżdżoną strukturę JSON**

**Opis problemu:**  
Przekształć drzewo binarne w obiekt JSON, gdzie każdy węzeł ma klucze `value`, `left` i `right`.

**Wejście (drzewo):**
```
      1
     / \
    2   3
   / \
  4   5
```

**Oczekiwane wyjście (JSON):**
```json
{
  "value": 1,
  "left": {
    "value": 2,
    "left": {
      "value": 4,
      "left": null,
      "right": null
    },
    "right": {
      "value": 5,
      "left": null,
      "right": null
    }
  },
  "right": {
    "value": 3,
    "left": null,
    "right": null
  }
}
```

**Kod wygenerowany przez GitHub Copilot:**
```java
import org.json.JSONObject;

public JSONObject treeToJson(TreeNode root) {
    if (root == null) return null;
    JSONObject json = new JSONObject();
    json.put("value", root.val);
    json.put("left", treeToJson(root.left));
    json.put("right", treeToJson(root.right));
    return json;
}
```

---

### Podsumowanie:
GitHub Copilot automatyzuje tworzenie *pure functions*, co oszczędza czas i redukuje błędy. Narzędzie wspiera transformacje danych, adaptacje formatów i operacje na jednostkach, sprawiając, że funkcje są bardziej spójne i przewidywalne.