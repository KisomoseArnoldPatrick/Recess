<?php

namespace App\Policies;

use App\Models\Participantchallenge;
use App\Models\User;
use Illuminate\Auth\Access\Response;

class ParticipantchallengePolicy
{
    /**
     * Determine whether the user can view any models.
     */
    public function viewAny(User $user): bool
    {
        //
    }

    /**
     * Determine whether the user can view the model.
     */
    public function view(User $user, Participantchallenge $participantchallenge): bool
    {
        //
    }

    /**
     * Determine whether the user can create models.
     */
    public function create(User $user): bool
    {
        //
    }

    /**
     * Determine whether the user can update the model.
     */
    public function update(User $user, Participantchallenge $participantchallenge): bool
    {
        //
    }

    /**
     * Determine whether the user can delete the model.
     */
    public function delete(User $user, Participantchallenge $participantchallenge): bool
    {
        //
    }

    /**
     * Determine whether the user can restore the model.
     */
    public function restore(User $user, Participantchallenge $participantchallenge): bool
    {
        //
    }

    /**
     * Determine whether the user can permanently delete the model.
     */
    public function forceDelete(User $user, Participantchallenge $participantchallenge): bool
    {
        //
    }
}
