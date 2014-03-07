(define (domain wumpusworld)
(:requirements :typing :adl)

(:types 
	player arrow room gold - object
)

(:predicates
	(has ?x)
	(adjacent ?x ?y)
	(at ?x ?y)
	(canShoot ?r1 ?r2)
	(wumpusAt ?r - room)
	(pitAt ?r - room)
	(breezeAt ?r - room)
	(stenchAt ?r - room)
	(isAlive)
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
					(and (canShoot ?from ?to) (isAlive)) 
					(and (has ?a) (at ?p ?from))
				)
	:effect (and 
				(not (has ?a)) 
				(not (wumpusAt ?to))
			)
)

(:action pickup
	:parameters (?r - room ?p - player ?g - gold)
	:precondition (and 
					(and (at ?g ?r) (isAlive)) 
					(at ?p ?r)
				)
	:effect (and 
				(not (at ?g ?r)) 
				(has ?g)
			)
)
)
