#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "avl_tree.h"

/*
typedef struct data { 
    char name[50];
    int age;
} DATA;

typedef struct tree { 
    DATA data;
    int height;
    struct tree *left;
    struct tree *right;
} TREE;*/

TREE *createNewLeaf(char *newName, int newAge);
int getLeafHeigh(TREE *leaf);
int calculateHeigh(TREE *leaf);
int calculateBalanceFactor(TREE *leaf);
TREE *rotateLeft(TREE *leaf);
TREE *rotateRight(TREE *leaf);
TREE *insertTree(TREE *tree, char *name, int age);
TREE *searchTree(TREE *tree, char *findName, int findAge);
void printTree(TREE* tree);
void deleteTree(TREE *tree);
void compare(char *string_1, char *string_2);
void freeTree(TREE **tree);

TREE *createNewLeaf(char *newName, int newAge) {

    TREE *newLeaf = malloc(sizeof(TREE));

    strcpy(newLeaf->data.name, newName);
    newLeaf->data.age = newAge;

    newLeaf->height = 1;
    newLeaf->left = NULL;
    newLeaf->right = NULL; 

    return newLeaf;
}

int getLeafHeigh(TREE *leaf) {

    if (leaf == NULL)
        return 0;
    
    else 
        return leaf->height;
}

int calculateHeigh(TREE *leaf) {
    
    int leftH, rightH;

    leftH = getLeafHeigh(leaf->left);
    rightH = getLeafHeigh(leaf->right);

    if (leftH > rightH)
        return leftH;
    else 
        return rightH;
}

int calculateBalanceFactor(TREE *leaf) {
    
    int balanceFactor;
    balanceFactor = ( getLeafHeigh(leaf->right) - getLeafHeigh(leaf->left) );

    return balanceFactor;
}

TREE *rotateLeft(TREE *leaf) {

    TREE* leafRight = leaf->right;	
	TREE* leafRightLeft = leafRight->left; 

    leaf->right = leafRightLeft;
	leafRight->left = leaf;

	leaf->height = 1 + calculateHeigh(leaf);
	leafRight->height = 1 + calculateHeigh(leafRight);

	return leafRight;
}

TREE *rotateRight(TREE *leaf) {

    TREE* leafLeft = leaf->left;	
	TREE* leafLeftRight = leafLeft->right;	
	
	leaf->left = leafLeftRight;
    leafLeft->right = leaf;

	leaf->height = 1 + calculateHeigh(leaf);
	leafLeft->height = 1 + calculateHeigh(leafLeft);

	return leafLeft;
}

TREE *insertTree(TREE *tree, char *name, int age) {

    int balanceFactor;

    //empty leaf
    if (tree == NULL){
        tree = createNewLeaf(name, age);
        return tree;
    }

    //go left   tree->data.name > data->name
    if ( strcmp(tree->data.name, name) == 1 ) {
        tree->left = insertTree(tree->left, name, age);
    }

    //go right      tree->data.name < data->name
    else if ( strcmp(tree->data.name, name) == -1 ) {
        tree->right = insertTree(tree->right, name, age);
    }

    //same data
    else {
        return tree;
    }

    //update height
    tree->height = 1 + calculateHeigh(tree);

    //check balance factor
    balanceFactor = calculateBalanceFactor(tree);

    //RR - left rotation            tree->right->data.name < name
	if (balanceFactor > 1 && strcmp(tree->right->data.name, name) == -1) {
        //printf("rotujemL\n\n");
        return rotateLeft(tree);
    }

    //LL - right rotation           tree->left->data.name > name
    if (balanceFactor < -1 && strcmp(tree->left->data.name, name) == 1) {
        //printf("rotujemR\n\n");
        return rotateRight(tree);
    }

	//RL - right & left rotation    tree->right->data.name > name
	if (balanceFactor > 1 && strcmp(tree->right->data.name, name) == 1) {
        //printf("rotujemRL\n\n");
		tree->right = rotateRight(tree->right);
		return rotateLeft(tree);
	}

    //LR - left & right rotation    tree->left->data.name < name
	if (balanceFactor < -1 && strcmp(tree->left->data.name, name) == -1) {
        //printf("rotujemLR\n\n");
		tree->left = rotateLeft(tree->left);
		return rotateRight(tree);
	}

    return tree;
}

TREE *searchTree(TREE *tree, char *findName, int findAge) {

	while (tree != NULL) {

        //data == tree->data
		if (strcmp(tree->data.name, findName) == 0 && tree->data.age == findAge) {
			return tree;
		}
        
        //serach in left subtree
		else if (strcmp(tree->data.name, findName) == 1) {
			tree = tree->left;
		}
        //serach in right subtree
		else {
			tree = tree->right;
		}
	}
	return NULL;
}

void printTree(TREE* tree) {
	
    if (tree != NULL) {
        printTree(tree->left);
        printTree(tree->right);
        printf("%s-%d, ", tree->data.name, tree->data.age);
        //printf("%s-%d, ", tree->data.name, tree->height);
	}
}

void freeTree(TREE **tree) {

    if ((*tree)->left != NULL ){
        freeTree( &((*tree)->left) );
    }

    if ((*tree)->right != NULL ){
        freeTree( &((*tree)->right) );
    }

    free((*tree));
    *tree = NULL;
}

void compare(char *string_1, char *string_2) {

    //stirng_2 is higher
    if ( strcmp(string_1, string_2) == -1 ) 
        printf("%s go right\n", string_2);
    
    //stirng_2 is lower
    if ( strcmp(string_1, string_2) == 1 )
            printf("%s go left\n", string_2);

    if ( strcmp(string_1, string_2) == 0 )
            printf("Strings are the same");
}
/*
int main() {

    TREE *tree = NULL;
    TREE *find = NULL;

    TREE **right, **left;

    char name[50];
    int age = 0;

    for (int i = 0; i < 10; i++) {
        scanf("%s", name);
        fgets(name, 50, stdin);
        tree = insert(tree, name, age);
    
    }
    printTreePostOrder(tree);

    left = &(tree->left);
    right = &(tree->right);

    freeTreeS(&tree);

    
    printf("\n** tree =  %s\n", tree);
    printf("\n** left =  %s\n", *(left));
    printf("\n** right =  %s\n", *(right));

    printf("End\n");
    return 0;
}*/