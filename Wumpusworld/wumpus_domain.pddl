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
	(sensedBreeze ?r)
	(sensedStench ?r)
	(isAlive)
)

(:action move
	:parameters (?p - player ?from - room ?to - room)
	:precondition (
					and
						(
							and 
								(and (isAlive) (at ?p ?from)) 
								(adjacent ?from ?to)
						)	
						(
							forall (?r - room)
                     			(
                     				and (
                     						imply (and (adjacent ?r ?to) (sensedStench ?r)) (wumpusAt ?to)
                     					)
                     					(
                     						imply (and (adjacent ?r ?to) (sensedBreeze ?r)) (pitAt ?to)
                     					)
                     			)
						)
)

	:effect (and
				(
					and (at ?p ?to) (not (at ?p ?from))
				)
				(
					when (or (wumpusAt ?to) (pitAt ?to)) (not (isAlive))
				)
			)
)

(:action shoot
	:parameters (?p - player, ?a - arrow, ?from - room, ?to - room)
	:precondition (and 
					(and (canShoot ?from ?to) (isAlive))
					(and (has ?a) (at ?p ?from))
				)
	:effect (and 
				(and 
					(not (has ?a)) 
					(not (wumpusAt ?to))
				)
				(
					forall (?r - room)
					(
						when (adjacent ?r ?to) (and (not (stenchAt ?to)) (not (sensedStench ?to)))
					)
				)
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

(:action sense
	:parameters (?r - room ?p - player )
	:precondition (and (at ?p ?r) (isAlive))
	:effect (
			and
				(
					when (stenchAt ?r) (sensedStench ?r)
				)
				(
					when (breezeAt ?r) (sensedBreeze ?r)
				)
		)
)

)
