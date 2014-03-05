(define (domain wumpusworld)
(:requirements :adl :typing :strips)

(:types 
	player arrow gold room sensor danger - object
	smell breeze - sensor
	wumpus pit - danger
)

(:predicates
	(has ?x)
	(adjacent ?x ?y)
	(at ?x ?y)
	(isAlive)
	(canShoot ?r1 ?r2)
	(wumpusAt ?r - room)
	(pitAt ?r - room)
	(stench ?r - room)
	(breeze ?r - room)
)

(:action move
	:parameters (?p - player ?origin - room ?dest - room)
	:precondition (and(adjacent ?origin ?dest) (at ?p ?origin))
	:effect (and(not(at ?p ?origin)) (at ?p ?dest))
)

(:action move
	:parameters (?p - player, ?from - room, ?to - room)
	:precondition (and 
		(and (isAlive) (at ?from ?p)) (adjacent ?from ?to))
	:effect (
		(when (wumpusAt ?to) 
		(not isAlive))

		(when (pitAt ?to) 
		(not isAlive))

		(and (at ?p ?to) (not (at ?p ?from)))
		)
)

(:action shoot
	:parameters (?p - player, ?a - arrow, ?from - room, ?to - room)
	:precondition (and 
		(and (isAlive) (canShoot ?from ?to)) 
		(has ?p ?a)
		)
	:effect (and (not (has ?a)) (not (wumpusAt ?to)))
)

(:action pickup
	:parameters (?g - gold ?r - room ?p - player)
	:precondition (and(at ?g ?r) (at ?p ?r))
	:effect (and (not (at ?g ?r)) (has ?g))
)


)
