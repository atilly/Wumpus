(define (domain wumpusworld)
(:requirements :adl :typing :strips :conditional-effects)

(:types 
	player arrow room gold - object
)

(:predicates
	(has ?x)
	(adjacent ?x ?y)
	(at ?x ?y)
	(isAlive)
	(canShoot ?r1 ?r2)
	(wumpusAt ?r - room)
	(pit ?r - room)
)

(:action move
	:parameters (?p - player, ?from - room, ?to - room)
	:precondition (and 
						(and 
							(and (isAlive) (at ?p ?from)) 
							(and (adjacent ?from ?to) (not (wumpusAt ?to)))
						)
						(not (pit ?to)
						)
				)
	:effect (and (at ?p ?to) (not (at ?p ?from)))
)

(:action shoot
	:parameters (?p - player, ?a - arrow, ?from - room, ?to - room)
	:precondition (and 
					(and (isAlive) (canShoot ?from ?to)) 
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
					(at ?g ?r) 
					(at ?p ?r)
				)
	:effect (and 
				(not (at ?g ?r)) 
				(has ?g)
			)
)

)
