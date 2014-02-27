(define (domain wumpusworld)
(:types player arrow gold location sensor danger - object
smell breeze - sensor
wumpus pit - danger)
(:predicates
       (have ?x)
	     (adjacent ?x ?y)
	     (at ?x ?y))

(:action shoot
:parameters (?a - arrow)
:precondition (have ?a)
:effect (not (have ?a)))

(:action move
:parameters (?p - player ?origin - location ?dest - location)
:precondition (and(adjacent ?origin ?dest) (at ?p ?origin))
:effect (and(not(at ?p ?origin)) (at ?p ?dest)))

(:action pickup
:parameters (?g - gold ?loc - location ?p - player)
:precondition (and(at ?g ?loc) (at ?p ?loc))
:effect (and(not (at ?g ?loc)) (have ?g))))
